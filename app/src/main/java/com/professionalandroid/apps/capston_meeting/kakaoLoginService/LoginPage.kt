package com.professionalandroid.apps.capston_meeting.kakaoLoginService

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.kakao.auth.ApiErrorCode
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.OptionalBoolean
import com.kakao.util.exception.KakaoException
import com.professionalandroid.apps.capston_meeting.*
import com.professionalandroid.apps.capston_meeting.retrofit.ConnectRetrofit
import com.professionalandroid.apps.capston_meeting.retrofit.email
import com.professionalandroid.apps.capston_meeting.retrofit.userid
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class LoginPage: AppCompatActivity(){
    private var sessionCallback: SessionCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        // 권한 요청
        tedPermission()

        sessionCallback = SessionCallback()
        Session.getCurrentSession().addCallback(sessionCallback)
        Session.getCurrentSession().checkAndImplicitOpen()
    }

    // 권한 요청
    private fun tedPermission(){
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {//설정해 놓은 위험권한(카메라 접근 등)이 허용된 경우 이곳을 실행
                Toast.makeText(this@LoginPage,"요청하신 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                Toast.makeText(this@LoginPage,"요청하신 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()            }

        }

        TedPermission.with(this)
            .setPermissionListener(permission)
            .setRationaleMessage("카메라 앱을 사용하시려면 권한을 허용해주세요.")
            .setDeniedMessage("권한을 거부하셨습니다.앱을 사용하시려면 [앱 설정]-[권한] 항목에서 권한을 허용해주세요.")
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
            .check()
    }

    override fun onDestroy() {
        super.onDestroy()

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession()
                .handleActivityResult(requestCode, resultCode, data)
        ) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    inner class SessionCallback : ISessionCallback {
        // 로그인에 성공한 상태
        override fun onSessionOpened() {
            requestMe()
        }

        // 로그인에 실패한 상태
        override fun onSessionOpenFailed(exception: KakaoException) {
            Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.message)
        }

        // 사용자 정보 요청
        fun requestMe() {
            UserManagement.getInstance()
                .me(object : MeV2ResponseCallback() {
                    override fun onSessionClosed(errorResult: ErrorResult) {
                        Log.e("KAKAO_API", "세션이 닫혀 있음: $errorResult")
                    }

                    override fun onFailure(errorResult: ErrorResult) {
                        val result = errorResult.errorCode

                        if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                            Toast.makeText(
                                applicationContext,
                                "네트워크 연결이 불안정합니다. 다시 시도해 주세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "로그인 도중 오류가 발생했습니다: " + errorResult.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onSuccess(result: MeV2Response) {
                        Log.i("KAKAO_API", "사용자 아이디: " + result.id)
                        val kakaoAccount = result.kakaoAccount
                        if (kakaoAccount != null) {

                            // 이메일
                            val email = kakaoAccount.email
                            if (email != null) {
                                Log.i("KAKAO_API", "email: $email")
                            } else if (kakaoAccount.emailNeedsAgreement() == OptionalBoolean.TRUE) {
                                // 동의 요청 후 이메일 획득 가능
                                // 단, 선택 동의로 설정되어 있다면 서비스 이용 시나리오 상에서 반드시 필요한 경우에만 요청해야 합니다.
                            } else {
                                // 이메일 획득 불가
                            }

                            // 프로필
                            val profile =
                                kakaoAccount.profile
                            if (profile != null) {
                                Log.d("KAKAO_API", "nickname: " + profile.nickname)
                                Log.d(
                                    "KAKAO_API",
                                    "profile image: " + profile.profileImageUrl
                                )
                                Log.d(
                                    "KAKAO_API",
                                    "thumbnail image: " + profile.thumbnailImageUrl
                                )
                            } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                                // 동의 요청 후 프로필 정보 획득 가능
                            } else {
                                // 프로필 획득 불가
                            }
                        }

                        val retrofitService = ConnectRetrofit(this@LoginPage)
                        val connect_server = retrofitService.retrofitService()
                        val data = email(kakaoAccount.email)

                        // retrofit 서버연결
                        connect_server.checkhMyID(data).enqueue(object: Callback<userid> {
                            override fun onFailure(call: Call<userid>, t: Throwable) {
                                Log.d("test","서버연결 실패 BoardActivity")
                            }

                            override fun onResponse(call: Call<userid>, response: Response<userid>) {
                                val user = response.body()!!
                                Log.d("test", "$user")

                                if(user._checked) {
                                    val intent = Intent(this@LoginPage, MainActivity::class.java)
                                    intent.putExtra("email", kakaoAccount.email)
                                    intent.putExtra("user", user.idx.toString())
                                    startActivity(intent)
                                    finish()
                                }
                                else{
                                    val intent = Intent(this@LoginPage, RegisterPage::class.java)
                                    intent.putExtra("email", kakaoAccount.email)
                                    startActivity(intent)
                                    finish()
                                }

                            }
                        })
                    }
                })
        }
    }
}
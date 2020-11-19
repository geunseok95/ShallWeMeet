package com.professionalandroid.apps.capston_meeting

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.professionalandroid.apps.capston_meeting.src.homePage.HomePage
import com.professionalandroid.apps.capston_meeting.src.kakaoLoginService.LoginPage
import com.professionalandroid.apps.capston_meeting.src.BaseActivity
import com.professionalandroid.apps.capston_meeting.src.bookmarkPage.BookmarkPage
import com.professionalandroid.apps.capston_meeting.src.checkPage.CheckPage
import com.professionalandroid.apps.capston_meeting.src.myInfoPage.MyInfoPage
import com.professionalandroid.apps.capston_meeting.src.successPage.SuccessPage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    companion object{

        var user: Long = -1
        lateinit var gender: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user = intent.getLongExtra("user", -1)
        gender = intent.getStringExtra("gender")!!

        // 상태바 투명하게 만들기
        window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.TRANSPARENT


        val homepage = HomePage()
        val checkPage =
            CheckPage()
        val bookmarkPage =
            BookmarkPage()
        val successpage =
            SuccessPage()
        val myinfo =
            MyInfoPage()

        val ft = supportFragmentManager

        ft.beginTransaction().replace(R.id.main_layout, homepage).commitAllowingStateLoss()

        bottom_navigation_bar.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.navigation_bar_home -> ft.beginTransaction().replace(R.id.main_layout, homepage).commitAllowingStateLoss()
                R.id.navigation_bar_check -> ft.beginTransaction().replace(R.id.main_layout, checkPage).commitAllowingStateLoss()
                R.id.navigation_bar_favorite -> ft.beginTransaction().replace(R.id.main_layout, bookmarkPage).commitAllowingStateLoss()
                R.id.navigation_bar_success -> ft.beginTransaction().replace(R.id.main_layout, successpage).commitAllowingStateLoss()
                R.id.navigation_bar_myinfo -> ft.beginTransaction().replace(R.id.main_layout, myinfo).commitAllowingStateLoss()
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    fun move_next_fragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun close_fragment(fragment: Fragment){
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.remove(fragment)
        transaction.commit()
        manager.popBackStack()
    }

    fun replace_fragment(layout_id: Int, fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(layout_id, fragment)
        transaction.commit()
    }



    fun logoutService(){
        Toast.makeText(applicationContext, "정상적으로 로그아웃되었습니다.", Toast.LENGTH_SHORT)
            .show()
        UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
            override fun onCompleteLogout() {
                val intent = Intent(this@MainActivity, LoginPage::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        })
    }

}
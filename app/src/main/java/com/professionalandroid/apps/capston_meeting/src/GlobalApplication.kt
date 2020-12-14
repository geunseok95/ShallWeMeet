package com.professionalandroid.apps.capston_meeting.src

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import com.kakao.auth.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        if (sSharedPreferences == null) {
            sSharedPreferences = applicationContext.getSharedPreferences(TAG, MODE_PRIVATE)
        }

        // Kakao Sdk 초기화
        KakaoSDK.init(KakaoSDKAdapter())
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    inner class KakaoSDKAdapter : KakaoAdapter() {
        override fun getSessionConfig(): ISessionConfig {
            return object : ISessionConfig {
                override fun getAuthTypes(): Array<AuthType> {
                    return arrayOf(AuthType.KAKAO_LOGIN_ALL)
                }

                override fun isUsingWebviewTimer(): Boolean {
                    return false
                }

                override fun isSecureMode(): Boolean {
                    return false
                }

                override fun getApprovalType(): ApprovalType? {
                    return ApprovalType.INDIVIDUAL
                }

                override fun isSaveFormData(): Boolean {
                    return true
                }
            }
        }

        // Application이 가지고 있는 정보를 얻기 위한 인터페이스
        override fun getApplicationConfig(): IApplicationConfig {
            return IApplicationConfig { globalApplicationContext }
        }
    }

    companion object {
        private var instance: GlobalApplication? = null
        val globalApplicationContext: GlobalApplication?
            get() {
                checkNotNull(instance) { "This Application does not inherit com.kakao.GlobalApplication" }
                return instance
            }

        // 테스트 서버 주소
        var BASE_URL = "http://mimansa.co.kr:8080"

        // 실서버 주소
        //    public static String BASE_URL = "https://template.softsquared.com/";

        var sSharedPreferences: SharedPreferences? = null

        // SharedPreferences 키 값
        var TAG = "TEMPLATE_APP"

        // JWT Token 값
        var FMC_TOKEN = "FMC-TOKEN"

        var fmc_token = ""

        //날짜 형식
        var DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

        var retrofit: Retrofit? = null
        fun retrofitService(context: Context): Retrofit? {
            // connect server
            if(retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl("https://shallwemeet.co.kr")
                    .client(
                        OkHttpClient.Builder().sslSocketFactory(
                            getPinnedCertSslSocketFactory(
                                context
                            )
                        )
                            .hostnameVerifier(NullHostNameVerifier()).build()
                    )
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit
    }

        private fun getPinnedCertSslSocketFactory(context: Context): SSLSocketFactory? {
            try {
                val am: AssetManager = context.resources.assets
                val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
                val caInput: InputStream = am.open("swm.cer")
                var ca: Certificate? = null
                try {
                    ca = cf.generateCertificate(caInput)
                    println("ca=" + ((ca) as X509Certificate).subjectDN)
                } catch (e: CertificateException) {
                    e.printStackTrace()
                } finally {
                    caInput.close()
                }

                val keyStoreType: String = KeyStore.getDefaultType()
                val keyStore: KeyStore = KeyStore.getInstance(keyStoreType)
                keyStore.load(null, null)
                if (ca == null) {
                    return null
                }
                keyStore.setCertificateEntry("ca", ca)

                val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
                val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm)
                tmf.init(keyStore)

                val sslContext: SSLContext = SSLContext.getInstance("TLS")
                sslContext.init(null, tmf.trustManagers, null)

                return sslContext.socketFactory
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: KeyStoreException) {
                e.printStackTrace()
            } catch (e: KeyManagementException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}
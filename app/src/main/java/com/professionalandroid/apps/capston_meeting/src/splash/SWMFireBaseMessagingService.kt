package com.professionalandroid.apps.capston_meeting.src.splash

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.professionalandroid.apps.capston_meeting.R
import java.io.UnsupportedEncodingException
import java.net.URLDecoder

class SWMFireBaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        @SuppressLint("LongLogTAG")//이건 뭐지?
        if(remoteMessage.notification != null){
            try {
                val title = URLDecoder.decode(remoteMessage.notification?.title, "EUC-KR")
                val body = URLDecoder.decode(remoteMessage.notification?.body, "EUC-KR")
                sendNotification(title, body)
                Log.d("test", "${title}, ${body}")

            }catch (ex: UnsupportedEncodingException){
                Log.d("test", ex.message.toString())
            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("test", token)
    }

    private fun sendNotification(title: String?, body: String){
        //어떤 모양으로 알림을 할지 설정한 다음 실제 폰 상단에 표시하도록 한다.
        //pendingIntent를 이용 알림을 클릭하면 열 앱의 액티비티를 설정해 준다.
        val intent = Intent(this, SplashScreenActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("Notification", body)
        }

        val CHANNEL_ID = "CollocNotification"
        val CHANNEL_NAME = "CollocChannel"
        val description = "This is Colloc channel"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val notificationManager: NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            channel.description = description
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.setShowBadge(false)
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Colloc Notification")
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(notificationSound)
            .setContentIntent(pendingIntent)

        notificationManager.notify(0, notificationBuilder.build())

    }
}
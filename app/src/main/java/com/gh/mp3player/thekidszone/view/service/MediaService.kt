package com.gh.mp3player.thekidszone.view.service

import CommonUtils
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.gh.mp3player.thekidszone.R
import com.gh.mp3player.thekidszone.model.MessageModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MediaService : Service() {
    private var appRunning = true
    private var notify1: Notification? = null
    private var notify2: Notification? = null

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private lateinit var manager: NotificationManager

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("WrongConstant", "ForegroundServiceType")
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Create...")
        createNotificationChannel()
        manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        appRunning = true

        val builder1 = NotificationCompat.Builder(this, CHANNEL_ID).setAutoCancel(false)
            .setSmallIcon(R.drawable.bg_splash)
            .setChannelId(CHANNEL_ID)
            .setContentTitle("Bạn có thông báo mới!")
            .setContentText("Chúng tôi sẽ gửi thông báo mỗi khi có tin nhắn trong nhóm !")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        notify1 = builder1.build()

        manager.notify(1001, notify1)

        coroutineScope.launch {
            updateView()
        }
    }

    private suspend fun updateView() {
        while (appRunning) {
            var s = CommonUtils.getInstance().getPref("NOTIFY").toString()
            s = s.removePrefix("-")
            val parts = s.split("-")
            for (i in parts) {
                delay(1000)
                FirebaseFirestore.getInstance().collection("GROUP").document(i).collection("MESSAGE")
                    .orderBy("timestamp", Query.Direction.DESCENDING).get()
                    .addOnCompleteListener { groupTask ->
                        if (groupTask.isSuccessful && groupTask.result.documents.isNotEmpty()) {
                            val data2 = groupTask.result.documents[0]
                            val name = data2?.get("Name") as String
                            val mes = data2["Message"] as String
                            val link = data2["Link"] as String
                            val email = data2["Email"] as String
                            val mesModel = MessageModel(name, mes, link, email)
                            if(CommonUtils.getInstance().getPref(i)!=mesModel.toString()) {
                                val builder2 = NotificationCompat.Builder(this, CHANNEL_ID).setAutoCancel(false)
                                    .setSmallIcon(R.drawable.bg_splash)
                                    .setChannelId(CHANNEL_ID)
                                    .setContentTitle("Bạn có tin nhắn mới!")
                                    .setContentText("Nhóm có id: $i đang trò chuyện rất sôi nổi. Tham gia ngay!")
                                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                notify2 = builder2.build()
                                manager.notify(1000, notify2)
                                CommonUtils.getInstance()
                                    .savePref(i, mesModel.toString())
                            }
                        }
                    }

            }
        }
    }

    private fun createNotificationChannel() {
        val description = getString(R.string.channel_description)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onDestroy() {
        appRunning = false
        coroutineScope.cancel()
        super.onDestroy()
    }

    companion object {
        private val TAG = MediaService::class.java.name
        private const val CHANNEL_ID = "TheKidsZone"
    }
}

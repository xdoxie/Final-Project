package com.example.callyourmother

import android.app.*
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.example.contacts.ContactManager
import com.example.contacts.ContactsAdapter
import com.example.contacts.R

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.util.Log
import com.example.contacts.Contact

import java.text.DateFormat
import java.util.Date

class AlarmNotificationReceiver: BroadcastReceiver() {
    companion object {
        // Notification ID to allow for future updates
        private const val MY_NOTIFICATION_ID = 1
        private val soundURI = Uri.parse("android.resource://course.examples.notification.statusbarwithcustomview/" + R.raw.notification)
        private const val mTickerText = "You have someone to call"
        private val mVibratePattern = longArrayOf(0, 200, 200, 300)
    }
    private lateinit var mNotificationManager: NotificationManager




    // Notification Sound and Vibration on Arrival

    private lateinit var mContext: Context
    private lateinit var mChannelID: String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent?) {
        mContext = context
        val intent=Intent(intent)
        val firstName= intent.getStringExtra(Contact.FIRSTNAME).toString()
        val lastName= intent.getStringExtra(Contact.LASTNAME).toString()
        val phoneNumber= intent.getStringExtra(Contact.NUMBER).toString()
        val notification = "Make sure to call $firstName $lastName @ $phoneNumber"


        mNotificationManager = mContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel()

        val notificationIntent = Intent(
            context,
                ContactManager::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val contentIntent = PendingIntent.getActivity(
                context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            val contentView = RemoteViews(
                context.packageName,
                R.layout.custom_notification
            )
        contentView.setTextViewText(R.id.notification_text,notification)
            val notificationBuilder = Notification.Builder(
                mContext, mChannelID
            )
                .setTicker(mTickerText)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setCustomContentView(contentView)


            mNotificationManager.notify(
                MY_NOTIFICATION_ID,
                notificationBuilder.build()
            )



    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        mNotificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        mChannelID = "${mContext.packageName}.channel_01"
        val name = "NotificationChannel"

        val description ="Notification Channel for this app"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(mChannelID, name, importance)

        mChannel.description = description
        mChannel.enableLights(true)

        // Sets the notification light color for notifications posted to this
        // channel, if the device supports this feature.
        mChannel.lightColor = Color.RED
        mChannel.enableVibration(true)
        mChannel.vibrationPattern = mVibratePattern

        mChannel.setSound(
           soundURI,
            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
        )

        mNotificationManager.createNotificationChannel(mChannel)



    }
}
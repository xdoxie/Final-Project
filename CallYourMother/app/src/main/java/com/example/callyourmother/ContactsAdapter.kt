package com.example.contacts

import android.app.*
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.provider.Settings.Global.getString
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.android.synthetic.main.custom_notification.view.*
import java.util.ArrayList

class ContactsAdapter(private val mContext: Context) : BaseAdapter()  {
    private val mContacts = ArrayList<Contact>()
    private lateinit var mChannelID: String
    private lateinit var mNotificationManager: NotificationManager

    private var mAlarmManager: AlarmManager? =null
    fun add(contact: Contact ) {

        mContacts.add(contact)
        notifyDataSetChanged()

    }
    fun clear() {

        mContacts.clear()
        notifyDataSetChanged()

    }



    override fun getItem(position: Int): Any {
       return mContacts[position]
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getCount(): Int {
        return mContacts.size
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        mNotificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mAlarmManager = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

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
    private var mContact: LayoutInflater = LayoutInflater.from(mContext)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val viewHolder=ViewHolder()
        val contact= mContacts[position]



            viewHolder.mContactLayout=mContact.inflate(R.layout.contact,parent,false) as RelativeLayout
            viewHolder.mNameView= viewHolder.mContactLayout!!.findViewById(R.id.nameView);
            viewHolder.mLastNameView= viewHolder.mContactLayout!!.findViewById(R.id.lastNameView);
            viewHolder.mPhoneNumberView=viewHolder.mContactLayout!!.findViewById((R.id.numberView))
            viewHolder.mReminderView=viewHolder.mContactLayout!!.findViewById(R.id.reminderView)


        viewHolder.position=position

        viewHolder.mNameView?.text= contact.firstName
        viewHolder.mLastNameView?.text=contact.lastName

        viewHolder.mPhoneNumberView?.text=contact.phoneNumber

        viewHolder.mReminderView?.text= contact.reminder.toString()
        if(convertView!=null){
            createNotificationChannel()
            val notificationIntent = Intent(
                mContext,
                ContactManager::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val contentIntent = PendingIntent.getActivity(
                mContext, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            val contentView = RemoteViews(
                mContext.packageName,
                R.layout.custom_notification
            )
            val notificationBuilder = Notification.Builder(
                mContext, mChannelID
            )
                .setTicker(mTickerText)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setCustomContentView(contentView)

            contentView.setTextViewText(R.id.notification_text, contact.notification())
            mNotificationManager.notify(
                MY_NOTIFICATION_ID,
                notificationBuilder.build()
            )

        }

        return viewHolder.mContactLayout
    }
    internal class ViewHolder {
        var position: Int = 0
        var mContactLayout: RelativeLayout? = null
        var mNameView: TextView? = null
        var mLastNameView: TextView? = null
        var mPhoneNumberView: TextView? = null
        var mReminderView: TextView? = null



    }
    companion object {
        private val DAY= 8640000
        private val SEVEN_DAYS = 604800000
        private val FOURTEEN_DAYS=1209600000
        private val MONTH= 2592000000
        private val mVibratePattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        private val soundURI = Uri.parse("android.resource://course.examples.notification.statusbarwithcustomview/" + R.raw.notification)
        private const val mTickerText = "You have someone to call"
        private const val MY_NOTIFICATION_ID = 1

        private val TAG = "Contacts-Project"
    }
}

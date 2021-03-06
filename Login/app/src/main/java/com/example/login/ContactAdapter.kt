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
import android.os.SystemClock
import android.provider.Settings.Global.getString
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import com.example.login.AlarmNotificationReceiver
import com.example.login.Contact
import com.example.login.R
import kotlinx.android.synthetic.main.custom_notification.view.*
import java.util.ArrayList

class ContactsAdapter(private val mContext: Context) : BaseAdapter()  {
    private val mContacts = ArrayList<Contact>()

    private var mAlarmManager: AlarmManager? =null
    fun add(contact: Contact ) {

        mContacts.add(contact)
        notifyDataSetChanged()

    }
    fun contains(contact: Contact): Boolean {
        return mContacts.contains(contact)
    }
    fun clear() {

        mContacts.clear()
        notifyDataSetChanged()

    }
    fun delete(position: Int){
        mContacts.removeAt(position)
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



    private var mContact: LayoutInflater = LayoutInflater.from(mContext)
    var repeat_interval: Long = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val viewHolder=ViewHolder()
        val contact= mContacts[position]

        mAlarmManager = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager





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
        private val DAY= 8640000.toLong()
        private val SEVEN_DAYS = 604800000.toLong()
        private val FOURTEEN_DAYS=1209600000.toLong()
        private val MONTH= 2592000000.toLong()
        private const val REPEAT_INTERVAL = 10 * 1000
        private val mVibratePattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        private val soundURI = Uri.parse("android.resource://course.examples.notification.statusbarwithcustomview/" + R.raw.notification)
        private const val mTickerText = "You have someone to call"
        private const val MY_NOTIFICATION_ID = 1

        private val TAG = "Contacts-Project"
    }
}
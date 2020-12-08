package com.example.login
import ContactsAdapter
import android.app.Activity
import android.app.AlarmManager
import android.app.ListActivity
import android.app.PendingIntent
import android.view.ViewGroup


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.login.AlarmNotificationReceiver
import java.io.*
import java.text.ParseException
import com.example.login.Contact.Reminder



class ContactManager: ListActivity() {
    internal lateinit var mAdapter: ContactsAdapter

    private lateinit var mNotificationReceiverPendingIntent: PendingIntent
    private var mAlarmManager: AlarmManager? =null
    var repeat_interval: Long = 0
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter = ContactsAdapter(applicationContext)
        mAlarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        listView.setFooterDividersEnabled(true)

        val footerView: View= LayoutInflater.from(this@ContactManager).inflate(R.layout.contacts_main,listView,false)

        listView.addFooterView(footerView)

        val activity= Intent(this@ContactManager,AddNewContact::class.java)

        footerView.setOnClickListener{startActivityForResult(activity,ADD_CONTACT_REQUEST)}
        listView.adapter=mAdapter



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Log.i(TAG, "Entered onActivityResult()")

        if (requestCode == ADD_CONTACT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val intent = Intent(data)
                val item = Contact(intent)
                if (item.reminder==Contact.Reminder.DAILY){
                    repeat_interval= DAY
                }else if (item.reminder==Contact.Reminder.WEEKLY){
                    repeat_interval= SEVEN_DAYS
                }else if (item.reminder==Contact.Reminder.BIWEEKLY){
                    repeat_interval= FOURTEEN_DAYS
                }else {
                    repeat_interval =MONTH
                }

                val mNotificationReceiverIntent = Intent(
                    this@ContactManager, AlarmNotificationReceiver::class.java
                )
                mNotificationReceiverIntent.putExtra(Contact.FIRSTNAME,item.firstName)
                mNotificationReceiverIntent.putExtra(Contact.LASTNAME,item.lastName)
                mNotificationReceiverIntent.putExtra(Contact.NUMBER,item.phoneNumber)
                mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(
                    this@ContactManager, 0, mNotificationReceiverIntent, 0
                )
                mAdapter.add(item)
                mAlarmManager?.setRepeating(
                    AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(),
                    REPEAT_INTERVAL.toLong(),
                    mNotificationReceiverPendingIntent
                )
                // if user submitted a new ToDoItem
                // Create a new ToDoItem from the data Intent
                // and then add it to the adapter
            }
        }
    }
    public override fun onResume() {
        super.onResume()

        // Load saved ToDoItems, if necessary

        if (mAdapter.count == 0)
            loadItems()
    }
    override fun onPause() {
        super.onPause()

        // Save ToDoItems

        saveItems()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all")
        menu.add(Menu.NONE, MENU_DUMP, Menu.NONE, "Dump to log")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            MENU_DELETE -> {
                mAdapter.clear()
                return true
            }
            MENU_DUMP -> {
                dump()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun dump() {
        for (i in 0 until mAdapter.count) {
            val data = (mAdapter.getItem(i) as Contact).toLog()
            Log.i(TAG,
                "Contact " + i + ": " + Contact.ITEM_SEP?.let { data.replace(it, ",") })
        }
    }

    // Load stored ToDoItems
    private fun loadItems() {
        var reader: BufferedReader? = null
        try {
            val fis = openFileInput(FILE_NAME)
            reader = BufferedReader(InputStreamReader(fis))

            var firstName: String? = null
            var lastName: String? = null
            var number: String? = null
            var reminder: String? = null

            do {
                firstName = reader.readLine();
                if (firstName == null)
                    break
                lastName= reader.readLine()
                number = reader.readLine()
                reminder = reader.readLine()
                mAdapter.add(Contact(firstName, lastName,
                    number, Reminder.valueOf(reminder)))

            }
            while (true)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        } finally {
            if (null != reader) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    // Save ToDoItems to file
    private fun saveItems() {
        var writer: PrintWriter? = null
        try {
            val fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            writer = PrintWriter(
                BufferedWriter(
                    OutputStreamWriter(
                        fos)
                )
            )

            for (idx in 0 until mAdapter.count) {

                writer.println(mAdapter.getItem(idx))

            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            writer?.close()
        }
    }
    companion object {
        private val DAY= 8640000.toLong()
        private val SEVEN_DAYS = 604800000.toLong()
        private val FOURTEEN_DAYS=1209600000.toLong()
        private val MONTH= 2592000000.toLong()
        private const val REPEAT_INTERVAL = 30 * 1000
        private val ADD_CONTACT_REQUEST = 0
        private val TAG = "Contacts-Project"
        private val FILE_NAME="ContactManager.txt"
        // IDs for menu items
        private val MENU_DELETE = Menu.FIRST
        private val MENU_DUMP = Menu.FIRST + 1
    }
}

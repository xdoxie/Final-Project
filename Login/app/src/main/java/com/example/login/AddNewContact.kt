package com.example.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import com.example.login.Contact.Reminder

class AddNewContact : Activity(){
    private var mReminderRadioGroup: RadioGroup? = null
    private var mFirstNameText: EditText? = null
    private var mLastNameText: EditText? = null
    private var mPhoneNumberText: EditText? = null
    private val mReminder: Reminder
        get() {

            when (mReminderRadioGroup!!.checkedRadioButtonId) {
                R.id.Daily -> {
                    return Reminder.DAILY
                }
                R.id.Weekly -> {
                    return Reminder.WEEKLY
                }
                R.id.BiWeekly -> {
                    return Reminder.BIWEEKLY
                }
                else -> {
                    return Reminder.MONTHLY
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_contact)

        mFirstNameText= findViewById<View>(R.id.FirstName) as EditText
        mLastNameText= findViewById<View>(R.id.LastName) as EditText
        mPhoneNumberText=findViewById<View>(R.id.PhoneNumber)as EditText
        mReminderRadioGroup= findViewById<View>(R.id.reminderGroup) as RadioGroup

        val cancelButton = findViewById<View>(R.id.cancelButton) as Button
        cancelButton.setOnClickListener { Log.i(TAG, "Entered cancelButton.OnClickListener.onClick()")
            setResult(RESULT_CANCELED)
            finish()
        }
        val submitButton = findViewById<View>(R.id.submitButton) as Button
        submitButton.setOnClickListener {
            Log.i(TAG, "Entered submitButton.OnClickListener.onClick()")


            val viewIntent = Intent(Intent.ACTION_VIEW)
            Contact.packageIntent(viewIntent, mFirstNameText!!.text.toString(),mLastNameText!!.text.toString(),mPhoneNumberText!!.text.toString(),mReminder)
            setResult(RESULT_OK,viewIntent)
            finish()
        }

    }
    companion object {
        private val DAY= 8640000
        private val SEVEN_DAYS = 604800000
        private val FOURTEEN_DAYS=1209600000
        private val MONTH= 2592000000
        private val TAG= "Contacts-Project"
    }

}
package com.example.login

import android.content.Intent
import android.telephony.PhoneNumberUtils
import java.time.DayOfWeek
import java.util.*
import java.util.logging.Level.parse

class Contact {
    var firstName= String()
    var lastName= String()
    var phoneNumber=String()
    var reminder= Reminder.DAILY
    enum class Reminder {
        DAILY,WEEKLY,BIWEEKLY,MONTHLY
    }
    internal constructor(firstName: String, lastName:String, phoneNumber: String, reminder: Reminder){
        this.firstName=firstName
        this.lastName=lastName
        this.phoneNumber=phoneNumber
        this.reminder=reminder
    }
    internal constructor(intent: Intent){
        firstName= intent.getStringExtra(FIRSTNAME).toString()
        lastName= intent.getStringExtra(LASTNAME).toString()
        phoneNumber= intent.getStringExtra(NUMBER).toString()
        reminder= Reminder.valueOf(intent.getStringExtra(REMINDER).toString())

    }
    fun notification(): String {
        return ("Make sure to call $firstName $lastName @ $phoneNumber")
    }
    override fun toString(): String {
        return (firstName + ITEM_SEP + lastName + ITEM_SEP + phoneNumber + ITEM_SEP
                + reminder)
    }
    fun toLog(): String {
        return ("First Name:" + firstName + ITEM_SEP + "Last Name:" + lastName
                + ITEM_SEP + "Number:" + phoneNumber + ITEM_SEP + "Reminder Frequency:"
                + reminder + "\n")
    }
    companion object {

        val ITEM_SEP = System.getProperty("line.separator")

        val FIRSTNAME = "firstname"
        val LASTNAME = "lastname"
        val NUMBER="number"
        val REMINDER="reminder"

        // Take a set of String data values and
        // package them for transport in an Intent

        fun packageIntent(intent: Intent, firstName: String,
                          lastName:String, phoneNumber: String, reminder: Reminder) {

            intent.putExtra(FIRSTNAME, firstName)
            intent.putExtra(LASTNAME, lastName)
            intent.putExtra(NUMBER, phoneNumber)
            intent.putExtra(REMINDER, reminder.toString())

        }
    }

}
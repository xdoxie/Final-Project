package com.example.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class ActivityHub : AppCompatActivity() {

    private lateinit var firstTitle : TextView
    private lateinit var instructions: TextView
    private lateinit var about: TextView
    private lateinit var contactsButton: Button
    private lateinit var addContactsButton: Button
    private lateinit var instructionsButton: Button
    private lateinit var aboutButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hub)
        window.decorView.setBackgroundColor(Color.WHITE);

        firstTitle = findViewById(R.id.firstTitle);
        contactsButton = findViewById(R.id.contactsButton)
        addContactsButton = findViewById(R.id.addContactButton)
        instructionsButton = findViewById(R.id.instructionsButton)
        aboutButton = findViewById(R.id.aboutButton)

        contactsButton.setOnClickListener {
            val intent = Intent(this, ContactManager::class.java)
            startActivity(intent);
        }

        addContactsButton.setOnClickListener {
            val intent = Intent(this, AddNewContact::class.java)
            startActivity(intent);
        }

        instructionsButton.setOnClickListener {

        }

        aboutButton.setOnClickListener {
        }
    }
}
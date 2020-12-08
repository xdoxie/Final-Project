package com.example.login

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
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
        window.decorView.setBackgroundColor(Color.WHITE)
        //setSupportActionBar(findViewById(R.id.ActivityToolbar))


        firstTitle = findViewById(R.id.firstTitle);
        contactsButton = findViewById(R.id.contactsButton)
        instructionsButton = findViewById(R.id.instructionsButton)
        aboutButton = findViewById(R.id.aboutButton)


        contactsButton!!.setOnClickListener {
            val intent = Intent(this, ContactManager::class.java)
            startActivity(intent);
        }


        instructionsButton!!.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/2vRaqw7Mc1Q"))
                    startActivity(i)
        }

        aboutButton.setOnClickListener {
            val i = Intent(this, ScrollingActivity::class.java)
            startActivity(i)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflator= menuInflater
        inflator.inflate(R.menu.menu_main,menu)
        return true
    }
}
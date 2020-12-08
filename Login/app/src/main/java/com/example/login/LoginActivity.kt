package com.example.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity :  AppCompatActivity(){
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    private var userEmail: EditText? = null
    private var userPassword: EditText? = null
    private var loginBtn: Button? = null
    private var registerBtn: Button? = null
    private var progressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()
        userEmail = findViewById(R.id.email)
        userPassword = findViewById(R.id.password)
        loginBtn = findViewById(R.id.login)
        registerBtn = findViewById<Button>(R.id.register)
        progressBar = findViewById(R.id.progressBar)

        loginBtn!!.setOnClickListener { loginUserAccount() }
        registerBtn!!.setOnClickListener {
            val i = Intent(this, RegistrationActivity::class.java)
            startActivity(i)
        }
    }

    private fun loginUserAccount() {
        progressBar!!.visibility = View.VISIBLE
        val userEmail = userEmail!!.text.toString()
        val password = this.userPassword!!.text.toString()

        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(applicationContext, "Please enter email", Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(applicationContext, "Please enter password", Toast.LENGTH_LONG).show()
            return
        }

        mAuth!!.signInWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener({
                progressBar!!.visibility = View.GONE

                if(it.isSuccessful){
                    Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, ActivityHub::class.java)
                    intent.putExtra(USER_EMAIL,userEmail)
                    startActivity(intent.putExtra(USER_ID, mAuth!!.uid))
                } else {
                    Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_LONG).show()
                }
            })
    }

    companion object {
        const val USER_EMAIL = "com.example.tesla.myhomelibrary.useremail"
        const val USER_ID = "com.example.tesla.myhomelibrary.userid"
    }
}
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
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase

class LoginActivity :  AppCompatActivity(){
    private var userEmail: EditText? = null
    private var userPassword: EditText? = null
    private var loginBtn: Button? = null
    private var progressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        userEmail = findViewById(R.id.email)
        userPassword = findViewById(R.id.password)
        loginBtn = findViewById(R.id.login)
        progressBar = findViewById(R.id.progressBar)

        loginBtn!!.setOnClickListener { loginUserAccount() }
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
        val intent = Intent(this, ActivityHub::class.java)
        startActivity(intent)
/*        mAuth!!.signInWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener({
                progressBar!!.visibility = View.GONE

                if(it.isSuccessful){
                    Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_LONG).show()
                    val i = Intent(this, DashboardActivity::class.java)
                    i.putExtra(USER_EMAIL,userEmail)

                    startActivity(i.putExtra(USER_ID, mAuth!!.uid))
                } else {
                    Toast.makeText(applicationContext, "Login failed. please try again later", Toast.LENGTH_LONG).show()
                }
            })*/
    }
}
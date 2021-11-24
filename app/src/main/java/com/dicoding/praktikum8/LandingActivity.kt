package com.dicoding.praktikum8

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LandingActivity : AppCompatActivity() {
    // Setup variable
    private val title:String = "Pilih Menu"
    private lateinit var btn1thread:Button
    private lateinit var btn2http:Button
    private lateinit var btn3progress:Button
    private lateinit var btn4login:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        // Set title action bar
        val actionBar = supportActionBar
        actionBar?.title = title

        // Set button 1 simple thread
        btn1thread = findViewById(R.id.landing_1)
        btn1thread.setOnClickListener {
            val intent = Intent(this@LandingActivity, SimpleThreadActivity::class.java)
            startActivity(intent)
        }

        // Set button 2 HTTP Connection
        btn2http = findViewById(R.id.landing_2)
        btn2http.setOnClickListener {
            val intent = Intent(this@LandingActivity, HttpConnectionActivity::class.java)
            startActivity(intent)
        }

        // Set button 4 login
        btn4login = findViewById(R.id.landing_4)
        btn4login.setOnClickListener {
            val intent = Intent(this@LandingActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
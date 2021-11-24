package com.dicoding.praktikum8

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    // Setup variable
    private lateinit var textDetail: TextView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Set detail data
        val actionBar = supportActionBar
        textDetail = findViewById(R.id.detail_text)
        if (intent.getStringExtra(TYPE).toString() == "login") {
            // Login
            actionBar?.title = "Login"
            textDetail.text = String.format(getString(R.string.selamat_datang_login), intent.getStringExtra(TEXT))
        } else if (intent.getStringExtra(TYPE).toString() == "daftar") {
            // Register
            actionBar?.title = "Register"
            textDetail.text = getString(R.string.selamat_datang_register, intent.getStringExtra(TEXT))
        }

        // Set button back
        btnBack = findViewById(R.id.detail_back)
        btnBack.setOnClickListener { onBackPressed() }
    }

    companion object {
        const val TEXT = "hayolo"
        const val TYPE = "jali"
    }
}
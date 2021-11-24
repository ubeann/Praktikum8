package com.dicoding.praktikum8

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    // Setup variable
    private val title:String = "Selamat Datang"
    private var progressPercentage:Int = 0
    private lateinit var progressBar:ProgressBar
    private lateinit var progressText:TextView
    private lateinit var username:TextInputLayout
    private lateinit var email:TextInputLayout
    private lateinit var password:TextInputLayout
    private lateinit var btnLogin:Button
    private lateinit var btnRegister:Button
    private lateinit var executor: Executor
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set title action bar
        val actionBar = supportActionBar
        actionBar?.title = title

        // Create thread using executor
        executor = Executors.newSingleThreadExecutor()
        handler = Handler(Looper.getMainLooper())

        // Set progress bar
        progressBar = findViewById(R.id.progressBar)
        progressText = findViewById(R.id.progressText)

        // Set button
        btnLogin = findViewById(R.id.login)
        btnRegister = findViewById(R.id.register)

        // Set login
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        btnLogin.setOnClickListener {
            createThread("login")
        }

        // Set register
        email = findViewById(R.id.email)
        btnRegister.setOnClickListener {
            createThread("daftar")
        }
    }

    override fun onPause() {
        username.editText?.setText("")
        password.editText?.setText("")
        email.editText?.setText("")
        super.onPause()
    }

    private fun createThread(type : String) {
        executor.execute {
            try {
                // Set visible loading
                handler.post {
                    progressBar.progress = 0
                    progressBar.visibility = View.VISIBLE
                    progressText.text = String.format(getString(R.string.loading), 0)
                    progressText.visibility = View.VISIBLE
                    settingButton(false)
                }

                // Simulate process in background thread
                for (i in 0..10) {
                    Thread.sleep(1000)
                    progressPercentage = if (i < 10) (i * 10..(i * 10)+10).random() else i * 10
                    handler.post {
                        //update ui in main thread
                        if (progressPercentage == 100) {
                            progressBar.visibility = View.INVISIBLE
                            progressText.visibility = View.INVISIBLE
                            settingButton(true)
                            val intent = Intent(this@MainActivity, DetailActivity::class.java)
                            if (type === "login") {
                                intent.putExtra(DetailActivity.TEXT, username.editText?.text.toString())
                            } else {
                                intent.putExtra(DetailActivity.TEXT, email.editText?.text.toString())
                            }
                            intent.putExtra(DetailActivity.TYPE, type)
                            startActivity(intent)
                        } else {
                            progressBar.progress = progressPercentage
                            progressText.text = String.format(getString(R.string.loading), progressPercentage)
                        }
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun settingButton(isEnable : Boolean) {
        btnLogin.isEnabled = isEnable
        btnRegister.isEnabled = isEnable
    }
}
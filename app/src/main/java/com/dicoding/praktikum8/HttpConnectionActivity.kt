package com.dicoding.praktikum8

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.LinearProgressIndicator
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class HttpConnectionActivity : AppCompatActivity() {
    // Setup variable
    private val title:String = "HTTP Connection"
    private var progressPercentage:Int = 0
    private lateinit var progressBar: LinearProgressIndicator
    private lateinit var progressText: TextView
    private lateinit var image:ImageView
    private lateinit var executor: Executor
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http_connection)

        // Set title action bar
        val actionBar = supportActionBar
        actionBar?.title = title

        // Set image
        image = findViewById(R.id.image)


        // Create thread using executor
        executor = Executors.newSingleThreadExecutor()
        handler = Handler(Looper.getMainLooper())

        // Setup progress variable
        progressBar = findViewById(R.id.progressBar)
        progressText = findViewById(R.id.progressText)

        // Get image when create activity
        getImage()
    }

    private fun getImage() {
        executor.execute {
            try {
                // Set visible loading
                handler.post {
                    progressBar.progress = 0
                    progressBar.visibility = View.VISIBLE
                    progressText.text = getString(R.string.loading_no_text)
                    progressText.visibility = View.VISIBLE
                }
                Thread.sleep(3500)
                handler.post {
                    Glide.with(this@HttpConnectionActivity)
                        .load("https://picsum.photos/1200")
                        .into(image)
                    showToast()
                    progressBar.visibility = View.INVISIBLE
                    progressText.visibility = View.INVISIBLE
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun showToast() {
        Toast.makeText(this,"Berhasil mengambil foto dari Internet", Toast.LENGTH_SHORT).show()
    }
}
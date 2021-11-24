package com.dicoding.praktikum8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class SimpleThreadActivity : AppCompatActivity() {
    // Setup variable
    private val title:String = "Thread Sederhana"
    private var progressPercentage:Int = 0
    private lateinit var progressBar: CircularProgressIndicator
    private lateinit var progressText: TextView
    private lateinit var btnCreate:Button
    private lateinit var snackBar: Snackbar
    private lateinit var executor: Executor
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_thread)

        // Set title action bar
        val actionBar = supportActionBar
        actionBar?.title = title

        // Create thread using executor
        executor = Executors.newSingleThreadExecutor()
        handler = Handler(Looper.getMainLooper())

        // Setup progress variable
        progressBar = findViewById(R.id.progressBar)
        progressText = findViewById(R.id.progressText)

        // Set button click listener
        btnCreate = findViewById(R.id.simple_thread_run)
        btnCreate.setOnClickListener { createChildThread(it) }
    }

    private fun createChildThread(view: View) {
        executor.execute {
            try {
                // Set visible loading
                handler.post {
                    progressBar.progress = 0
                    progressBar.visibility = View.VISIBLE
                    progressText.text = String.format(getString(R.string.loading), 0)
                    progressText.visibility = View.VISIBLE
                    btnCreate.isEnabled = false
                    createSnackBar(view, getString(R.string.child_thread_created))
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
                            btnCreate.isEnabled = true
                            createSnackBar(view, getString(R.string.child_thread_success))
                        } else {
                            progressBar.progress = progressPercentage
                            progressText.text = String.format(getString(R.string.loading), progressPercentage)
                        }
                    }
                }
            } catch (e: InterruptedException) {
                createSnackBar(view, getString(R.string.child_thread_error))
                e.printStackTrace()
            }
        }
    }

    private fun createSnackBar(view: View, text: String) {
        snackBar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
        snackBar.setAction("Tutup") { snackBar.dismiss() }
        snackBar.show()
    }
}
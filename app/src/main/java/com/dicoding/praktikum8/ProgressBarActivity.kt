package com.dicoding.praktikum8

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ProgressBarActivity : AppCompatActivity() {
    // Setup variable
    private val title:String = "Progress Bar"
    private var progressPercentage:Int = 0
    private var max:Int = 0
    private var increment:Int = 0
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private lateinit var maxValue: TextInputLayout
    private lateinit var incrementBy: TextInputLayout
    private lateinit var btnStart: Button
    private lateinit var executor: Executor
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_bar)

        // Set title action bar
        val actionBar = supportActionBar
        actionBar?.title = title

        // Create thread using executor
        executor = Executors.newSingleThreadExecutor()
        handler = Handler(Looper.getMainLooper())

        // Set progress bar
        progressBar = findViewById(R.id.progressBar)
        progressText = findViewById(R.id.progressText)

        // Set value
        maxValue = findViewById(R.id.max_value)
        incrementBy = findViewById(R.id.increment_value)

        // Set button
        btnStart = findViewById(R.id.start_progress_bar)
        btnStart.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(it.windowToken, 0)
            createProgressBar()
        }
    }

    private fun createProgressBar() {
        executor.execute {
            try {
                // Set visible loading
                handler.post {
                    progressBar.progress = 0
                    progressBar.visibility = View.VISIBLE
                    progressText.text = String.format(getString(R.string.loading), 0)
                    progressText.visibility = View.VISIBLE
                    btnStart.isEnabled = false
                }

                // Get value
                max = if (maxValue.editText?.text.toString() == "") 100 else maxValue.editText?.text.toString().toInt()
                increment = if (incrementBy.editText?.text.toString() == "") 10 else incrementBy.editText?.text.toString().toInt()

                // Simulate process in background thread
                for (i in 0..max step increment) {
                    Thread.sleep(500)
                    progressPercentage = (i*100)/max
                    handler.post {
                        // Update ui in main thread
                        if (progressPercentage == 100) {
                            progressBar.visibility = View.INVISIBLE
                            progressText.visibility = View.INVISIBLE
                            btnStart.isEnabled = true
                            showDialog(max, increment)
                        } else {
                            progressBar.progress = progressPercentage
                            progressText.text = String.format(getString(R.string.loading_nominal), progressPercentage, i, max)
                        }
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun showDialog(max: Int, increment:Int) {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.title_progress_bar))
            .setMessage(resources.getString(R.string.text_progress_bar, max, increment))
            .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
            }
            .show()
    }
}
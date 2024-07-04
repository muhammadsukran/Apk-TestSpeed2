package com.example.testspeed2

import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.net.InetAddress

class MainActivity : AppCompatActivity() {
    private lateinit var pingTextView: TextView
    private lateinit var batteryTextView: TextView
    private lateinit var fpsTextView: TextView
    private lateinit var startButton: Button

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pingTextView = findViewById(R.id.pingTextView)
        batteryTextView = findViewById(R.id.batteryTextView)
        fpsTextView = findViewById(R.id.fpsTextView)
        startButton = findViewById(R.id.startButton)

        // Memulai listener FPS saat aplikasi dibuka
        FPSUtils.startFrameRateListener()

        startButton.setOnClickListener {
            BackgroundService.stopBackgroundProcesses(this)
            BackgroundService.refreshDevice(this)
            BackgroundService.optimizeFPS(this)

            // Memanggil getPing() secara asynchronous menggunakan AsyncTask
            PingTask(object : PingTask.PingListener {
                override fun onPingResult(result: String) {
                    pingTextView.text = result
                }
            }).execute()

            batteryTextView.text = BatteryUtils.getBatteryLevel(this)
            fpsTextView.text = FPSUtils.getCurrentFPS()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Menghentikan listener FPS saat aplikasi ditutup
        FPSUtils.stopFrameRateListener()
    }

    private class PingTask(private val listener: PingListener) : AsyncTask<Void, Void, String>() {

        interface PingListener {
            fun onPingResult(result: String)
        }

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void?): String {
            val ipAddress = "google.com" // Ganti dengan alamat IP atau host yang ingin Anda ping
            var pingResult = ""

            try {
                val inet = InetAddress.getByName(ipAddress)
                val startTime = System.currentTimeMillis()

                if (inet.isReachable(5000)) { // Timeout dalam milidetik
                    val endTime = System.currentTimeMillis()
                    val pingTime = endTime - startTime
                    pingResult = "Ping: $pingTime ms"
                } else {
                    pingResult = "Ping: Timeout"
                }
            } catch (e: IOException) {
                e.printStackTrace()
                pingResult = "Ping: Error"
            }

            return pingResult
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: String) {
            listener.onPingResult(result)
        }
    }
}

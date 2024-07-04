package com.example.testspeed2

import android.os.AsyncTask
import java.io.IOException
import java.net.InetAddress

object NetworkUtils {

    interface PingListener {
        fun onPingResult(result: String)
    }

    fun getPing(listener: PingListener) {
        PingTask(listener).execute()
    }

    private class PingTask(private val listener: PingListener) : AsyncTask<Void, Void, String>() {

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

        override fun onPostExecute(result: String) {
            listener.onPingResult(result)
        }
    }
}

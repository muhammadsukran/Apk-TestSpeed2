package com.example.testspeed2

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.webkit.WebView
import java.io.File

object BackgroundService {
    fun stopBackgroundProcesses(context: Context) {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningProcesses = activityManager.runningAppProcesses
        for (process in runningProcesses) {
            activityManager.killBackgroundProcesses(process.processName)
        }
    }

    fun refreshDevice(context: Context) {
        // Membersihkan cache aplikasi
        clearApplicationCache(context)

        // Menghentikan proses latar belakang yang tidak diperlukan
        stopBackgroundProcesses(context)

        // Memaksa garbage collection
        System.gc()
    }

    private fun clearApplicationCache(context: Context) {
        try {
            val dir = context.cacheDir
            deleteDir(dir)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            return dir.delete()
        } else if (dir != null && dir.isFile) {
            return dir.delete()
        } else {
            return false
        }
    }

    fun optimizeFPS(context: Context) {
        // Mengurangi beban kerja CPU/GPU dengan mengoptimalkan pengaturan WebView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.setWebContentsDebuggingEnabled(false)
            WebView(context).clearCache(true)
        }

        // Menurunkan kualitas grafis aplikasi jika memungkinkan
        // Ini adalah tempat di mana Anda dapat menambahkan logika untuk menurunkan kualitas grafis atau menyesuaikan pengaturan lain yang dapat meningkatkan FPS

        // Memastikan tidak ada aplikasi berat yang berjalan di latar belakang
        stopBackgroundProcesses(context)

        // Mengoptimalkan memori
        System.gc()
    }
}

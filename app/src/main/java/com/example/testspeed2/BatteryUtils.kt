package com.example.testspeed2

import android.content.Context
import android.os.BatteryManager

object BatteryUtils {
    fun getBatteryLevel(context: Context): String {
        val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val batteryLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        return "Sisa Baterai: $batteryLevel%"
    }
}

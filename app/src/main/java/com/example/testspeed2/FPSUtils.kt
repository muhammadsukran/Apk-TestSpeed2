package com.example.testspeed2

import android.os.Build
import android.view.Choreographer

object FPSUtils {
    private var lastFrameTimeNanos: Long = 0
    private var frameCount = 0
    private var fps: Double = 0.0

    fun startFrameRateListener() {
        lastFrameTimeNanos = 0
        frameCount = 0

        Choreographer.getInstance().postFrameCallback(object : Choreographer.FrameCallback {
            override fun doFrame(frameTimeNanos: Long) {
                if (lastFrameTimeNanos != 0L) {
                    val frameInterval = frameTimeNanos - lastFrameTimeNanos
                    fps = 1_000_000_000.0 / frameInterval
                }
                lastFrameTimeNanos = frameTimeNanos
                frameCount++
                Choreographer.getInstance().postFrameCallback(this)
            }
        })
    }

    fun stopFrameRateListener() {
        Choreographer.getInstance().removeFrameCallback(null)
    }

    fun getCurrentFPS(): String {
        return "FPS: ${fps.toInt()}"
    }
}

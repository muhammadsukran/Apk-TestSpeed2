package com.example.testspeed2

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {
    lateinit var mSocket: Socket
    @Synchronized
    fun setSocket() {
        try {
            mSocket = IO.socket("http://10.0.2.2:3000") // Gunakan 10.0.2.2 untuk mengakses localhost dari emulator Android
        } catch (e: URISyntaxException) {
            Log.e("SocketHandler", "URISyntaxException: ${e.message}")
        }
    }
    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }
    @Synchronized
    fun establishConnection() {
        mSocket.connect()
    }
    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }
}

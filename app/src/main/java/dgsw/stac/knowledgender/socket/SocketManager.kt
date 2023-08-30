package dgsw.stac.knowledgender.socket

import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter

class SocketManager {
    companion object {
        @Volatile
        private var SOCKET: Socket? = null
        val isConnected: Boolean get() = SOCKET?.connected() == true

        fun getSocket(token: String): Socket {
            val options = IO.Options()
            options.query = "authorization=$token"
            return SOCKET ?: synchronized(this) {
                val socket = IO.socket("ws://52.78.246.108:8085",options)
                SOCKET = socket
                socket
            }
        }


        fun on(name: String, listener: Emitter.Listener) {
            SOCKET?.on(name, listener)
        }
        fun sendMessage(name: String,content: String) {
            SOCKET?.emit(name,content)
        }

        fun establishConnection() {
            SOCKET?.connect()
        }

        fun closeConnection() {
            SOCKET?.disconnect()
        }
    }
}

data class Message(
    val message: String,
    val roomId: String
)
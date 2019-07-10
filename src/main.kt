import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import kotlin.concurrent.thread

const val SERVER_PORT = 1337

fun main(args: Array<String>) {

    println("Hello  World!")
    val socketListener = ServerSocket(SERVER_PORT)
    var clientNumber = 0

    socketListener.use {
        while (true) {
            val socket = socketListener.accept()
            thread(start = true) {
                clientNumber++
                val thisClient = clientNumber
                val output = PrintWriter(socket.getOutputStream(), true)
                val input = BufferedReader(InputStreamReader(socket.getInputStream()))
                println("New Client: ${socket.inetAddress}")
                output.println("Hello from the future!!")
                val line = input.readLine()
                println("Client ${socket.inetAddress} said:\n$line")
                output.println("Bye!")
                println("Closing client ${socket.inetAddress} connection.")
                socket.close()
            }
        }
    }

}
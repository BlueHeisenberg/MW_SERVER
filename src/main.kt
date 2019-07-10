import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import kotlin.concurrent.thread
import kotlin.random.Random

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
                while(true) {
                    println("Sending:")
                    output.println("${Random.nextInt(0, 10000000)}")
                    val line = input.readLine()
                    println("Client ${socket.inetAddress} said:\n$line")
                    if(line == null) break
                }
                socket.close()
                output.println("Bye!")
                println("Closing client ${socket.inetAddress} connection.")
            }
        }
    }

}
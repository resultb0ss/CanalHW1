import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

suspend fun main() {
    val text = Storage().text
    val str = text.split("!", ",", ".", ":", ";").map { it.split(" ").toList() }
    val channel = Channel<String>()
    val time = measureTimeMillis {
        coroutineScope {
            launch {
                str.getList(channel)
                }

            launch {
                for (i in channel){
                println("Получены даные $i") }
            }
        }
    }
    println("Время затраченное на выполнение: $time")



}

suspend infix fun List<List<String>>.getList(channel: SendChannel<String>) {
    for (i in this.indices) {
        for (j in this[i]) {
            delay(100L)
            channel.send(j)
        }
    }
    channel.close()
}

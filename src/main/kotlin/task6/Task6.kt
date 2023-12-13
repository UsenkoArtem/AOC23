package task6

import readText
import readTextByLines


private fun task1(cards: String): Int {
    val time =
        cards.split("\n").first().substringAfter(":").split(" ").filter { it.isNotEmpty() }.map { it.trim().toLong() }
    val dist = cards.split("\n")[1].substringAfter(":").split(" ").filter { it.isNotEmpty() }.map { it.trim().toLong() }
    var r = 1
    for (i in time.indices) {
        val t = time[i]
        var res = 0
        for (j in 0..<t) {
            val curD = (t - j) * j
            if (curD > dist[i]) {
                res++
            }
        }
        println(res)
        r *= res

    }

    return r
}

private fun task2(cards: List<String>): Long {
    val time =
        cards.first().substringAfter(":").split(" ").filter { it.isNotEmpty() }.map { it.trim() }
            .joinToString("") { it }
            .toLong()
    val dist =
        cards[1].substringAfter(":").split(" ").filter { it.isNotEmpty() }.map { it.trim() }.joinToString("") { it }
            .toLong()

    var res = 0L
    for (j in 0..<time) {
        val curD = (time - j) * j
        if (curD > dist) {
            res++
        }
    }
    println(res)
    return res
}

fun main() {
    val lines = readTextByLines("6.txt")
    println(task1(readText("6.txt")))
    println(task2(lines))
}
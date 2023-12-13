package task8

import readTextByLines


private fun getTime(startPos: String, move: Map<String, Pair<String, String>>, inst: List<Char>): Long {
    var index = 0
    var steps = 0L
    var curPos = startPos
    while (!curPos.endsWith("Z")) {
        steps++
        val (a, b) = move[curPos]!!
        curPos = if (inst[index] == 'L') {
            a
        } else {
            b
        }

        index++
        if (index >= inst.size) {
            index = 0
        }
    }
    return steps
}

private fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

private fun lcm(a: Long, b: Long): Long {
    return a * b / gcd(a, b)
}

private fun task1(cards: List<String>): Long {
    val inst = cards.first()

    val move = cards.drop(2).map {
        //AAA = (BBB, CCC)
        val (a, b) = it.split(" = ")
        val (c, d) = b.replace("(", "").replace(")", "").replace(",", "").split(" ")
        a.trim() to (c.trim() to d.trim())
    }.associate { it }.toMutableMap()
    var curPos = move.keys.filter { it.endsWith("A") }
    val result = curPos.map { getTime(it, move, inst.toList()) }
    return result.drop(1).fold(result[0]) { acc, i -> lcm(acc, i) }

}

private fun task2(cards: List<String>): Long {
    return 0
}

fun main() {
    val lines = readTextByLines("8.txt")
    println(task1(lines))
    println(task2(lines))
}
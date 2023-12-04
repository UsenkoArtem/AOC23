package task4

import readTextByLines


private fun task1(cards: List<String>): Int {
    return cards.sumOf {
        val winNumber = it.substringBefore("|").trim().split(" ").map(String::trim).filter(String::isNotEmpty)
        val myNumber = it.substringAfter("|").trim().split(" ").map(String::trim).filter(String::isNotEmpty)

        var res = 0
        myNumber.forEach { num ->
            if (winNumber.contains(num)) {
                if (res == 0)
                    res = 1
                else res *= 2
            }
        }
        res
    }

}

private fun task2(cards: List<String>): Int {

    val copyArray = Array(cards.size) { 1 }
    cards.mapIndexed { index, card ->
        val winNumber = card.substringBefore("|").trim().split(" ").map { it.trim() }.filter { it != "" }
        val myNumber = card.substringAfter("|").trim().split(" ").map { it.trim() }.filter { it != "" }

        var res = 0
        myNumber.forEach { num ->
            if (winNumber.contains(num)) {
                res += 1
            }
        }
        for (i in 1..res) {
            copyArray[index + i] = copyArray[index] + copyArray[index + i]
        }
    }
    return copyArray.sum()
}

fun main() {
    val lines = readTextByLines("4.txt")
    println(task1(lines))
    println(task2(lines))
}
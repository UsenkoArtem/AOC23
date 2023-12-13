package task9

import readTextByLines


private fun findLastNumber(number: List<Long>): Long {
    val newPos = mutableListOf(mutableListOf<Long>())
    newPos[0] = number.toMutableList()
    var index = 0
    while (!newPos[index].all { it == 0L }) {
        val dif = List(newPos[index].drop(1).size) { j -> newPos[index][j + 1] - newPos[index][j] }
        newPos.add(dif.toMutableList())
        index++
    }

    return newPos.sumOf { it.last() }
}

private fun findFistNumber(number: List<Long>): Long {
    val newPos = mutableListOf(mutableListOf<Long>())
    newPos[0] = number.toMutableList()
    var index = 0
    while (!newPos[index].all { it == 0L }) {
        val dif = List(newPos[index].drop(1).size) { j -> newPos[index][j + 1] - newPos[index][j] }
        newPos.add(dif.toMutableList())
        index++
    }


    var newFirst = 0L
    newPos.reversed().forEach {
        newFirst = it.first() - newFirst
    }

    //println(newFirst)

    return newFirst
}

private fun task1(cards: List<String>): Long {
    return cards.sumOf {
        findLastNumber(it.split(" ").map { it.toLong() })
    }
}

private fun task2(cards: List<String>): Long {
    return cards.sumOf {
        findFistNumber(it.split(" ").map { it.toLong() })
    }
}

fun main() {
    val lines = readTextByLines("9.txt")
    println(task1(lines))
    println(task2(lines))
}
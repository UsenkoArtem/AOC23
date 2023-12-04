package task3

import readTextByLines


private fun isDigit(c: String): Boolean {
    return c.toIntOrNull() != null
}

private fun findAndReplaceNumber(array: Array<Array<String>>, i1: Int, j1: Int, neighbor: String): Int {
    var number = array[i1][j1].toInt()
    val i = i1
    val j = j1
    var left = j - 1
    var powCount = 1.0
    while (left >= 0 && array[i][left].toIntOrNull() != null) {
        number += array[i][left].toInt() * Math.pow(10.0, powCount).toInt()
        array[i][left] = "."
        powCount++
        left--
    }
    var right = j + 1
    while (right < array[i].size && array[i][right].toIntOrNull() != null) {
        number = number * 10 + array[i][right].toInt()
        array[i][right] = "."
        right++
    }
    println(number)
    return number
}

private fun task1(array: Array<Array<String>>): Int {
    var sum = 0
    val neighbor = listOf(1 to 0, 1 to -1, 1 to 1, 0 to 1, 0 to -1, -1 to 0, -1 to -1, -1 to 1)
    for (i in array.indices) {
        for (j in array[i].indices) {
            if (array[i][j] != "." && !isDigit(array[i][j])) {
                neighbor.forEach {
                    val neighbor = array[i + it.first][j + it.second]
                    if (isDigit(neighbor)) {
                        sum += findAndReplaceNumber(array, i + it.first, j + it.second, neighbor)
                    }
                }
            }
        }
    }
    return sum
}

private fun task2(array: Array<Array<String>>): Long {
    var sum = 0L
    val neighbor = listOf(1 to 0, 1 to -1, 1 to 1, 0 to 1, 0 to -1, -1 to 0, -1 to -1, -1 to 1)
    for (i in array.indices) {
        for (j in array[i].indices) {
            if (array[i][j] != "." && !isDigit(array[i][j]) && array[i][j] == "*") {
                var result = 1L
                var count = 0
                neighbor.forEach {
                    val neighbor = array[i + it.first][j + it.second]
                    if (isDigit(neighbor)) {
                        result *= findAndReplaceNumber(array, i + it.first, j + it.second, neighbor)
                        count++
                    }
                }
                if (count == 2) {
                    sum += result
                }
            }
        }
    }
    return sum
}

fun main() {
    val lines = readTextByLines("3.txt")
    val array_t1 = Array(lines.size) { Array(lines[0].length) { "" } }
    val array_t2 = Array(lines.size) { Array(lines[0].length) { "" } }
    lines.forEachIndexed { i, s ->
        s.forEachIndexed { j, c ->
            array_t1[i][j] = c.toString()
            array_t2[i][j] = c.toString()
        }
    }

    val result = task1(array_t1)
    val result1 = task2(array_t2)
    println(result)
    println(result1)
}
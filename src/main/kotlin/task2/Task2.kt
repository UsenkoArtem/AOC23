package task2

import readTextByLines
import kotlin.math.max


private fun task1(lines: List<String>): Int {
    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14
    return lines.mapIndexed { index, it ->
        val game = it.substringAfter(":").trim()
        val round = game.split(";")
        val isOk = round.count {
            val colors = it.split(",")
            colors.map(String::trim).map { it.split(" ") }.all {
                when (it[1]) {
                    "red" -> it[0].toInt() <= maxRed
                    "green" -> it[0].toInt() <= maxGreen
                    "blue" -> it[0].toInt() <= maxBlue
                    else -> false
                }
            }
        } == round.count()
        if (isOk) {
            index + 1
        } else {
            0
        }
    }.sum()
}

private fun task2(lines: List<String>): Int {
    return lines.sumOf { it ->
        var maxRed = 0
        var maxGreen = 0
        var maxBlue = 0
        it.substringAfter(":").trim().split(";").forEach {
            val colors = it.split(",")
            colors.forEach {
                val colorCount = it.trim().substringBefore(" ")
                val color = it.trim().substringAfter(" ")
                when (color) {
                    "red" -> maxRed = max(colorCount.toInt(), maxRed)
                    "green" -> maxGreen = max(colorCount.toInt(), maxGreen)
                    "blue" -> maxBlue = max(colorCount.toInt(), maxBlue)
                    else -> false
                }
            }
        }
        maxRed * maxGreen * maxBlue
    }
}

fun main() {
    val lines = readTextByLines("2.txt")
    val result = task1(lines)
    val result1 = task2(lines)
    println(result)
    println(result1)
}
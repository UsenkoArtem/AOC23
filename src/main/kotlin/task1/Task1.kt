package task1

import readTextByLines


private fun task1(lines: List<String>): Int {
    return lines.sumOf {
        it.first(Char::isDigit).toString().toInt() * 10 +
            it.last(Char::isDigit).toString().toInt()
    }
}

private fun getDigit(s: String): Int {
    val correctDigit = listOf(
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "one", "two",
        "three", "four", "five", "six", "seven", "eight", "nine"
    )

    val digit = correctDigit.map { s.indexOf(it) to it }.filter { it.first != -1 }.minByOrNull { it.first }!!.second
    var firstDigit = correctDigit.indexOf(digit)
    if (firstDigit > 9) {
        firstDigit -= 9
    }

    val digitL =
        correctDigit.map { s.lastIndexOf(it) to it }.filter { it.first != -1 }.maxByOrNull { it.first }!!.second
    var lastDigit = correctDigit.indexOf(digitL)
    if (lastDigit > 9) {
        lastDigit -= 9
    }

    return firstDigit * 10 + lastDigit
}

private fun task2(lines: List<String>): Int {
    return lines.sumOf {
        getDigit(it)
    }
}


fun main() {
    val lines = readTextByLines("1.txt")
    val result = task1(lines)
    println(result)
    val lines2 = readTextByLines("1.txt")
    val result2 = task2(lines2)
    println(result2)
}
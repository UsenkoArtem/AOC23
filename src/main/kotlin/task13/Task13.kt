package task13

import readText


private fun calculateDiff(s: String, t: String): Int {
    var diff = 0
    for (i in s.indices) {
        if (s[i] != t[i]) {
            diff++
        }
    }
    return diff
}

private fun isVerticalMirror(input: List<String>, maxErrorCount: Int = 0): Int? {
    val count = input[0].length

    val w = (1..<count).firstOrNull {
        var errorCount = 0
        for (i in input.indices) {
            val l = input[i].substring(0, it)
            val r = input[i].substring(it)
            val min = minOf(l.length, r.length)
            val subL = l.substring(l.length - min, l.length).reversed()
            val subR = r.substring(0, min)

            errorCount += calculateDiff(subL, subR)

            if (errorCount > maxErrorCount) {
                return@firstOrNull false
            }
        }
        return@firstOrNull errorCount == maxErrorCount
    }
    return w
}

private fun isHorizontalMirror(input: List<String>, maxErrorCount: Int): Int? {
    val count = input.size
    val w = (1..<count).firstOrNull {
        var isOk = false
        var j = 1
        var errorCount = 0
        for (i in it..<count) {
            if (it - j < 0) {
                break
            }

            errorCount += calculateDiff(input[it - j], input[i])
            if (errorCount > maxErrorCount) {
                break
            }
            j++
            isOk = true
        }
        return@firstOrNull isOk && errorCount == maxErrorCount
    }
    return w
}

private fun task1(input: List<String>): Int {
    val vertical = input.mapNotNull { isVerticalMirror(it.split("\n"), 0) }
    val horizontal = input.mapNotNull { isHorizontalMirror(it.split("\n"), 0) }

    val verticalCount = vertical.sumOf { it }
    val horizontalCount = horizontal.sumOf { it }

    println(verticalCount)
    println(horizontalCount)

    return verticalCount + horizontalCount * 100
}

private fun task2(input: List<String>): Int {
    val vertical = input.mapNotNull { isVerticalMirror(it.split("\n"), 1) }
    val horizontal = input.mapNotNull { isHorizontalMirror(it.split("\n"), 1) }

    val verticalCount = vertical.sumOf { it }
    val horizontalCount = horizontal.sumOf { it }

    println(verticalCount)
    println(horizontalCount)

    return verticalCount + horizontalCount * 100
}

fun main() {
    val lines = readText("13.txt")
    println(task1(lines.split("\n\n")))
    println(task2(lines.split("\n\n")))
}
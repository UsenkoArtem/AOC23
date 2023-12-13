package task12

import readTextByLines


val cache = mutableMapOf<Pair<List<Char>, List<Int>>, Long>()

private fun checkLine(
    lineLeft: List<Char>,
    rules: List<Int>,
    s: String
): Long {

    if (Pair(lineLeft, rules) in cache) {
        return cache[Pair(lineLeft, rules)]!!
    }

    if (lineLeft.isEmpty()) {
        if (rules.isEmpty()) {
//            println(s)
            return 1
        }
        return 0
    }

    if (lineLeft.first() == '#') {
        if (rules.isEmpty()) {
            cache[Pair(lineLeft, rules)] = 0
            return 0
        }
        val needHashInRow = rules.first()
        if (lineLeft.size < needHashInRow) {
            cache[Pair(lineLeft, rules)] = 0
            return 0
        }

        if (lineLeft.take(needHashInRow).any { it == '.' }) {
            cache[Pair(lineLeft, rules)] = 0
            return 0
        }


        val newLineLeft = lineLeft.drop(needHashInRow)
        val newS = s + (0..<needHashInRow).joinToString("") { "#" }
        if (newLineLeft.isNotEmpty() && newLineLeft.first() == '#') {
            cache[Pair(lineLeft, rules)] = 0
            return 0
        }
        if (newLineLeft.isNotEmpty() && newLineLeft.first() == '?') {
            return checkLine(newLineLeft.drop(1), rules.drop(1), "$newS.")
        }
        return checkLine(
            lineLeft.drop(needHashInRow),
            rules.drop(1),
            newS
        )
    }

    if (lineLeft.first() == '.') {
        return checkLine(lineLeft.drop(1), rules, "$s.")
    }

    val dot = checkLine(lineLeft.drop(1), rules, "$s.")

    val hash = if (rules.isNotEmpty()) {
        val needHashInRow = rules.first()

        if (lineLeft.size >= needHashInRow && !lineLeft.take(needHashInRow).any { it == '.' }) {
            var newLineLeft = lineLeft.drop(needHashInRow)
            val newS = s + (0..<needHashInRow).joinToString("") { "#" }
            if (newLineLeft.isNotEmpty() && newLineLeft.first() == '#') {
                cache[Pair(lineLeft, rules)] = 0
                0
            } else

                if (newLineLeft.isNotEmpty() && newLineLeft.first() == '?') {
                    newLineLeft = listOf('.') + newLineLeft.drop(1)
                    checkLine(newLineLeft.drop(1), rules.drop(1), "$newS.")
                } else {
                    checkLine(newLineLeft, rules.drop(1), newS)
                }

        } else 0
    } else 0
    cache[Pair(lineLeft, rules)] = dot + hash
    return dot + hash


}

private fun task1(map: List<String>): Long {
    return map.sumOf { line ->
        val w = checkLine(
            line.substringBefore(" ").toList(),
            line.substringAfter(" ").split(",").map { it.toInt() },

            ""
        ).toLong()
        println("$line $w")
        w
    }
}

private fun task2(input: List<String>): Long {
    return input.sumOf { line ->
        var newLine = ""
        (0..4).forEach { _ ->
            if (newLine != "")
                newLine += "?"
            newLine += line.substringBefore(" ")
        }
        val rules = line.substringAfter(" ").split(",").map { it.toInt() }
        val multipleRules = mutableListOf<Int>()
        (0..4).forEach { _ ->
            multipleRules.addAll(rules)
        }
        val w = checkLine(
            newLine.toList(),
            multipleRules,

            ""
        ).toLong()
        //println("$line $w")
        w
    }
}

fun main() {
    val lines = readTextByLines("12.txt")
    println(task1(lines))
    println(task2(lines))
}
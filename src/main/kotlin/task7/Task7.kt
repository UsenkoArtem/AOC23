package task7

import readTextByLines

private fun cardPowerInternal(card: String): Int {
    val sorted = card.toCharArray().sorted()
    val grouped = sorted.groupBy { it }
    if (grouped.size == 1) {
        return 7
    }
    if (grouped.size == 2) {
        if (grouped.values.any { it.all { it == 'J' } })
            return 7

        if (grouped.values.any { it.size == 4 }) {
            return 6
        }

        return 5
    }
    if (grouped.size == 3) {
        if (grouped.values.any { it.size == 3 }) {
            return 4
        }
        return 3
    }
    if (grouped.size == 4) {
        return 2
    }
    return 1
}

fun cardPower(card: String): Int {


    if (card.contains("J")) {
        val allCardType = listOf(
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            'T',
            'J',
            'Q',
            'K',
            'A'
        )
        return allCardType.maxOf {
            val newCard = card.replace("J", it.toString())
            cardPowerInternal(newCard)
        }
    }

    return cardPowerInternal(card)
}

fun compare(a: Pair<String, Long>, b: Pair<String, Long>): Int {
    val cardA = a.first
    val cardB = b.first
    val powerA = cardPower(cardA)
    val powerB = cardPower(cardB)
    if (powerA > powerB) {
        return 1
    }
    if (powerA < powerB) {
        return -1
    }


    for (i in cardA.indices) {
        val a = cardA[i]
        val b = cardB[i]
        val com = cardCompare(a, b)
        if (com != 0) {
            return com
        }
    }
    return 0
}

fun cPower(a: Char): Int {
    return when (a) {
        '2' -> 1
        '3' -> 2
        '4' -> 3
        '5' -> 4
        '6' -> 5
        '7' -> 6
        '8' -> 7
        '9' -> 8
        'T' -> 9
        'J' -> 0
        'Q' -> 11
        'K' -> 12
        'A' -> 13
        else -> -1
    }
}

fun cardCompare(a: Char, b: Char): Int {
    val powerA = cPower(a)
    val powerB = cPower(b)
    if (powerA > powerB) {
        return 1
    }
    if (powerA < powerB) {
        return -1
    }
    return 0
}

private fun task1(cards: List<String>): Int {
    val pair = cards.map {
        val (a, b) = it.split(" ").map { it.trim() }
        a to b.toLong()
    }
    val sorted = pair.sortedWith { a, b -> compare(a, b) }
    println(sorted)
    return sorted.mapIndexed { index, pair -> pair.second * (index + 1) }.sum().toInt()
}

private fun task2(cards: List<String>): Long {
    return 0
}

fun main() {
    val lines = readTextByLines("7.txt")
    println(task1(lines))
    println(task2(lines))
}
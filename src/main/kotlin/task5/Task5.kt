package task5

import readText
import readTextByLines


private fun task1(cards: String): Long {

    fun extract(s: String): List<Long> {
        val regex = Regex("-?\\d+")
        return regex.findAll(s).map { it.value.toLong() }.toList()
    }

    fun getBreaks(m: List<List<Long>>): List<Long> {
        val b = mutableSetOf<Long>()
        for ((d, s, n) in m) {
            b.addAll(listOf(s - 1, s + n - 1))
        }
        return b.sorted()
    }

    fun breakRange(lo: Long, hi: Long, breaks: List<Long>): List<Pair<Long, Long>> {
        val ranges = mutableListOf<Pair<Long, Long>>()
        var low = lo
        for (b in breaks) {
            if (b in (low + 1)..<hi) {
                ranges.add(Pair(low, b))
                low = b + 1
            }
        }
        if (low < hi) {
            ranges.add(Pair(low, hi))
        }
        return ranges
    }

    fun lookup(m: List<List<Long>>, i: Long): Long {
        for ((d, s, n) in m) {
            if (s <= i && i < s + n) {
                return d + (i - s)
            }
        }
        return i
    }

    val data = cards.split("\n\n").map { it.trim().split("\n") }
    val seeds = extract(data[0][0])
    val maps = mutableListOf<List<List<Long>>>()

    for (lines in data.subList(1, data.size)) {
        val m = lines.filter { it.isNotBlank() }.map { extract(it) }

        maps.add(m.drop(1))
    }

    val iranges = mutableListOf<Pair<Long, Long>>()
    var i = 0
    while (i < seeds.size) {
        iranges.add(Pair(seeds[i], seeds[i] + seeds[i + 1]))
        i += 2
    }

    val nums = mutableListOf<Pair<Long, Long>>()
    for (irange in iranges) {
        var rs = listOf(irange)
        for (m in maps) {
            val breaks = getBreaks(m)
            val ir2 = rs.flatMap { (lo, hi) -> breakRange(lo, hi, breaks) }
            val rsi = rs
            rs = ir2.map { (x, y) -> Pair(lookup(m, x), lookup(m, y)) }
            println("$rsi -> $rs")
        }
        nums.addAll(rs)
    }

    println(nums)
    return nums.minOf { it.first }
}

private fun task2(cards: List<String>): Long {
    val seeds =
        cards.first().substringAfter("seeds:").split(" ").map { it.trim() }.filter { it != "" }.map { it.toLong() }
    val ints = cards.drop(2)
    return seeds.minBy { seed ->

        var index = 1
        var currentSeed = seed
        var isMoved = false
        while (index < ints.size) {
            if (ints[index] == "") {
                index += 2
                isMoved = false
                continue
            }
            if (isMoved) {
                index++
                continue
            }
            val line = ints[index].split(" ").map { it.trim() }.filter { it != "" }
            val seendNumber = line[0].toLong()
            val start = line[1].toLong()
            val count = line[2].toLong()

            if (currentSeed >= start && currentSeed <= start + count) {
                currentSeed = seendNumber + (currentSeed - start)
                isMoved = true
            }
            index++
        }
        //    println(currentSeed)
        currentSeed
    }
}

fun main() {
    val lines = readTextByLines("5.txt")
    println(task1(readText("5.txt")))
    println(task2(lines))
}
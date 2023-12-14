package task14

import readText

private fun moveU(map: List<CharArray>): List<CharArray> {
    for (j in map.indices) {
        for (i in map[j].indices) {
            if (map[i][j] == '#' || map[i][j] == 'O') {
                continue
            }
            var pos = -1
            for (k in i + 1..<map[j].size) {
                if (map[k][j] == '#') {
                    break
                }
                if (map[k][j] == 'O') {
                    pos = k
                    break
                }
            }
            if (pos != -1) {
                map[pos][j] = '.'
                map[i][j] = 'O'
            }
        }
    }
    return map
}

private fun moveR(map: List<CharArray>): List<CharArray> {
    for (i in map.indices) {
        for (j in map[i].indices) {
            if (map[i][j] == '#' || map[i][j] == 'O') {
                continue
            }
            var pos = -1
            for (k in j + 1..<map[j].size) {
                if (map[i][k] == '#') {
                    break
                }
                if (map[i][k] == 'O') {
                    pos = k
                    break
                }
            }
            if (pos != -1) {
                map[i][pos] = '.'
                map[i][j] = 'O'
            }
        }
    }
    return map
}

private fun moveD(map: List<CharArray>): List<CharArray> {
    for (j in map.indices) {
        for (i in map[j].size - 1 downTo 0) {
            if (map[i][j] == '#' || map[i][j] == 'O') {
                continue
            }
            var pos = -1
            for (k in i - 1 downTo 0) {
                if (map[k][j] == '#') {
                    break
                }
                if (map[k][j] == 'O') {
                    pos = k
                    break
                }
            }
            if (pos != -1) {
                map[pos][j] = '.'
                map[i][j] = 'O'
            }
        }
    }
    return map
}

private fun moveL(map: List<CharArray>): List<CharArray> {
    for (i in map.indices) {
        for (j in map[i].size - 1 downTo 0) {
            if (map[i][j] == '#' || map[i][j] == 'O') {
                continue
            }
            var pos = -1
            for (k in j - 1 downTo 0) {
                if (map[i][k] == '#') {
                    break
                }
                if (map[i][k] == 'O') {
                    pos = k
                    break
                }
            }
            if (pos != -1) {
                map[i][pos] = '.'
                map[i][j] = 'O'
            }
        }
    }
    return map
}

private fun task1(input: List<String>): Int {
    val map = moveU(input.map { it.toCharArray() })
    return map.mapIndexed { i, s -> s.count { c -> c == 'O' } * (map.size - i) }.sum()
}

private val seen = hashMapOf<String, Int>()

private fun calculateHash(map: List<CharArray>): String {
    val string = map.joinToString("") { it.joinToString("") }
    val md5Bytes = java.security.MessageDigest.getInstance("MD5").digest(string.toByteArray(Charsets.UTF_8))
    return String(md5Bytes)
}

private fun cycle(map: List<CharArray>) {
    moveU(map)
    moveR(map)
    moveD(map)
    moveL(map)
}

private fun task2(input: List<String>): Int {
    var map = input.map { it.toCharArray() }
    val cycleCount = 1000
    var index = 1
    seen[calculateHash(map)] = 0
    val array = mutableListOf(map)
    var result = 0 to 0
    while (index < cycleCount) {
        cycle(map)
        val hash = calculateHash(map)
        if (seen.contains(hash)) {
            result = seen[hash]!! to index
            break
        }
        seen[hash] = index
        array.add(map.map { it.copyOf() })
        index++
    }
    //88769
    //88680
    println(result)
    val cycleStart = result.first
    val cycleSize = result.second - result.first
    var cycleIndex = (cycleCount - cycleStart) % cycleSize + cycleStart
    map = array[cycleIndex]

    return map.mapIndexed { i, s -> s.count { c -> c == 'O' } * (map.size - i) }.sum()
}

fun main() {
    val lines = readText("14.txt")
    println(task1(lines.split("\n")))
    println(task2(lines.split("\n")))
}
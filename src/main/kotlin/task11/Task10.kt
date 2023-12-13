package task11

import readTextByLines
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executors

private fun buildMap(oldMap: List<List<Char>>): Pair<Array<Boolean>, Array<Boolean>> {
    val isCopyColumn = Array(oldMap.first().size) { false }
    val isCopyRow = Array(oldMap.size) { false }
    oldMap.forEachIndexed { index, s ->
        isCopyRow[index] = s.all { it == '.' }
    }

    for (i in oldMap.first().indices) {
        var allColumnIsDot = true
        for (element in oldMap) {
            val curPos = element[i]
            allColumnIsDot = allColumnIsDot && curPos == '.'
        }

        isCopyColumn[i] = allColumnIsDot

    }
    return isCopyRow to isCopyColumn
}

private val cache = mutableMapOf<Pair<Pair<Int, Int>, Pair<Int, Int>>, Int>()

fun findMinPath(
    s: Pair<Int, Int>, e: Pair<Int, Int>, map: List<List<Char>>,
    isCopyRow: Array<Boolean>,
    isCopyColumn: Array<Boolean>,
    overHead: Int = 0
): Long {
    val queue = PriorityQueue<Pair<Long, Pair<Int, Int>>>(compareBy { it.first })
    val visited = mutableSetOf(s)
    queue.add(0L to s)
    while (queue.isNotEmpty()) {
        val (dist, cur) = queue.poll()!!
        if (cur == e) return dist
        for (d in listOf(0 to 1, 0 to -1, 1 to 0)) {
            val newPoint = cur.first + d.first to cur.second + d.second
            if (newPoint.second !in 0..<map[0].size || newPoint.first !in map.indices) continue
            if (newPoint in visited) continue
            visited.add(newPoint)
            if (d == 1 to 0) {
                if (isCopyRow[cur.first])
                    queue.add(dist + 1 + overHead to newPoint)
                else
                    queue.add(dist + 1 to newPoint)
            }
            if (d == 0 to 1) {
                if (isCopyColumn[cur.second])
                    queue.add(dist + 1 + overHead to newPoint)
                else
                    queue.add(dist + 1 to newPoint)
            }

            if (d == 0 to -1) {
                if (isCopyColumn[newPoint.second])
                    queue.add(dist + 1 + overHead to newPoint)
                else
                    queue.add(dist + 1 to newPoint)
            }
        }
    }
    return -1
}

private fun task1(map: List<List<Char>>, overHead: Int): Long {
    val (isCopyRow, isCopyColumn) = buildMap(map)
    val allPos = map.flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, c ->
            if (c == '#') {
                y to x
            } else null
        }
    }
    val resultList = ConcurrentLinkedQueue<Long>()
    val taskExecutor = Executors.newFixedThreadPool(8)
    for (i in allPos.indices)
        for (j in i + 1..<allPos.size) {
            taskExecutor.submit {
                resultList.add(findMinPath(allPos[i], allPos[j], map, isCopyRow, isCopyColumn, overHead))
            }
        }

    taskExecutor.shutdown()
    taskExecutor.awaitTermination(1000, java.util.concurrent.TimeUnit.SECONDS)

    return resultList.sum()
}

private fun task2(cards: List<String>): Long {
    return 0
}

fun main() {
    val lines = readTextByLines("11.txt")
    println(task1(lines.map { it.toList() }, 1))
    println(task1(lines.map { it.toList() }, 1000000 - 1))
}
package aoc_2020.day_3

import java.io.File

fun main() {
    val input = File("src/main/resources/inputs/day_3.txt").readLines()
    val crossedTrees = countCrossedTrees(3, 1, input)
    println("Part 1 - Crossed Trees : $crossedTrees")
    val slopes = listOf(
        Slope(1, 1),
        Slope(3, 1),
        Slope(5, 1),
        Slope(7, 1),
        Slope(1, 2)
    )
    println("Part 2 - Crossed Trees : ${countCrossedTreesOfMultipleSlopes(slopes, input)}")
}

fun countCrossedTrees(stepsToRight: Int, stepsToDown: Int, path: List<String>): Int {
    val crossedSquares = path.filterIndexed { index, s ->
        index % stepsToDown == 0 && s[index / stepsToDown * stepsToRight % s.length] == '#'
    }
    return crossedSquares.size
}

fun countCrossedTreesOfMultipleSlopes(slopes: List<Slope>, path: List<String>): Long {
    val crossedTrees = slopes.map {
        val countCrossedTrees = countCrossedTrees(it.stepsToRight, it.stepsToDown, path)
        countCrossedTrees.toLong()
    }

    return crossedTrees.reduce { acc, trees -> acc * trees }
}

data class Slope(
    val stepsToRight: Int,
    val stepsToDown: Int
)

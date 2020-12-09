package aoc_2020.day_7

import java.io.File


fun main() {
    val input = File("src/main/resources/inputs/day_7.txt").readLines()

    val bags = input.map {
        val bagAndNestedBags = it.split("bags contain")
        val bagColor = bagAndNestedBags.first().trim()
        val otherBags = bagAndNestedBags.last().split(",")
        if (otherBags.size == 1 && otherBags.first().trim().take(2) == "no") {
            Bag(bagColor, emptyMap())
        } else {
            val otherBagsColorWithCount = otherBags.map {
                val detail = it.trim().split(" ")
                val bagCount = detail.first().toInt()
                val color = detail.drop(1).dropLast(1).joinToString(" ").trim()
                Pair(color, bagCount)
            }
            Bag(bagColor, otherBagsColorWithCount.toMap())
        }
    }

    println("Part 1 - ${addParentBags("shiny gold", bags).size}")
    println("Part 2 - ${countBags("shiny gold", bags)}")
}

data class Bag(
    val color: String,
    val bags: Map<String, Int>
)

fun addParentBags(givenColor: String, bags: List<Bag>): Set<String> {
    val coverBags = mutableSetOf<String>()
    for ((color, subBags) in bags) {
        if (givenColor in subBags.keys) {
            coverBags.add(color)
            coverBags.addAll(addParentBags(color, bags))
        }
    }
    return coverBags
}

//new approach
fun countBags(givenColor: String, bags: List<Bag>): Int {
    val innerBags = bags.find { it.color == givenColor }?.bags!!
    return innerBags.values.sum() +
            innerBags.entries.sumBy { (color, count) ->
                count * countBags(color, bags)
            }
}

//OLD Approacj
fun countBags1(givenColor: String, bags: List<Bag>): Int {
    val innerBags = bags.find { it.color == givenColor }?.bags!!

    return if (innerBags.isEmpty()) {
        0
    } else {
        var total = innerBags.values.sum()

        println("Color : $givenColor  Ans: $total")
        innerBags.forEach {
            total += it.value * countBags1(it.key, bags)
        }
        total;
    }
}

import kotlin.math.abs

fun main() {

    // Read the test input from the `src/Day01_test.txt` file.
    val testInput = readInput("Day01_test")

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")

    fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
        // Split each line and convert to two separate lists
        val listA = mutableListOf<Int>()
        val listB = mutableListOf<Int>()

        // Split each line and convert to two separate lists
        input.forEach { line ->
            val (a, b) = line.split("   ").map { it.toInt() }
            listA.add(a)
            listB.add(b)
        }
        return listA to listB
    }

    fun calculateDifference(listA: List<Int>, listB: List<Int>): Int {
        // Work with copies of lists to avoid modifying original inputs
        val mutableA = listA.toMutableList()
        val mutableB = listB.toMutableList()
        var sum = 0

        // Find the lowest value in listA and the lowest value in listB.
        // Find the difference between the two values and add it to the sum.
        while (mutableA.isNotEmpty() && mutableB.isNotEmpty()) {
            // Find the minimum values in both lists
            val minA = mutableA.minOrNull() ?: 0
            val minB = mutableB.minOrNull() ?: 0

            // Add the absolute difference to the sum
            sum += abs(minA - minB)

            // Remove the used element from lists
            mutableA.remove(minA)
            mutableB.remove(minB)
        }
        return sum
    }

    fun calculateMultiplication(listA: List<Int>, listB: List<Int>): Int {
        return listA.sumOf { a -> listB.count { it == a } * a }
    }

    fun part1(input: List<String>): Int {
        val (listA, listB) = parseInput(input)
        return calculateDifference(listA, listB)
    }

    fun part2(input: List<String>): Int {
        val (listA, listB) = parseInput(input)
        return calculateMultiplication(listA, listB)
    }

    // Print the solution for both parts of the puzzle.
    part1(input).println()
    part2(input).println()
}

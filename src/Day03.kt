private const val MUL_REGEX = """mul\((\d{1,3}),(\d{1,3})\)"""
private const val DO_REGEX = """do\(\)"""
private const val DONT_REGEX = """don't\(\)"""
private val COMBINED_REGEX = """$MUL_REGEX|$DO_REGEX|$DONT_REGEX""".toRegex()

fun main() {
    // Read the test input and input files
    val testInput = readInput("Day03_test")
    val input = readInput("Day03")
    val inputAsString = readInputAsString("Day03")

    // Helper function to calculate sum for Part 1
    fun calculateMulSum(corruptedMemory: String): Int =
        MUL_REGEX.toRegex().findAll(corruptedMemory)
            .sumOf { match ->
                val (x, y) = match.destructured
                x.toInt() * y.toInt()
            }

    // Helper function to calculate sum for Part 2 with toggleable state
    fun calculateMulSumPart2(corruptedMemory: String): Int {
        var sum = 0
        var enabled = true

        // Process each match from the combined regex
        COMBINED_REGEX.findAll(corruptedMemory).forEach { match ->
            when (match.value) {
                "do()" -> enabled = true
                "don't()" -> enabled = false
                else -> if (enabled) {
                    val (x, y) = match.destructured
                    sum += x.toInt() * y.toInt()
                }
            }
        }
        return sum
    }

    // Part 1: Process each line independently
    fun part1(input: List<String>): Int =
        input.sumOf(::calculateMulSum)

    // Part 2: Process the entire input string to maintain state beween lines
    fun part2(input: String): Int =
        calculateMulSumPart2(input)

    // Print solutions
    println(part1(input))
    println(part2(inputAsString))
}
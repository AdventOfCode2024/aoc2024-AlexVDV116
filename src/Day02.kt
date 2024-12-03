fun main() {

    // Checks if a sequence is valid, returns true if it is safe
    fun isSafe(numbers: List<Int>): Boolean {
        var increase: Boolean? = null

        // For each pair of adjacent numbers in a line
        return numbers.zipWithNext().all { (current, next) ->
            // Determine sequence type on first pair
            if (increase == null) {
                increase = when {
                    next > current -> true // Increasing
                    next < current -> false // Decreasing
                    else -> return@all false // Invalid if equal
                }
            }

            // Validate the step by checking the difference
            when (increase) {
                true -> next - current in 1..3 // Valid increase
                false -> current - next in 1..3 // Valid decrease
                else -> false // Invalid step
            }
        }
    }

    fun part1(input: List<String>): Int {
        return input.count { line ->
            val numbers = line.split(" ").map { it.toInt() }
            isSafe(numbers)
        }
    }

    fun part2(input: List<String>): Int {
        return input.count { line ->
            val numbers = line.split(" ").map { it.toInt() }

            // Check if the original sequence is safe
            if (isSafe(numbers)) return@count true

            // Check if removing one number makes the sequence safe
            numbers.indices.any { index ->
                val modifiedNumbers = numbers.toMutableList().apply { removeAt(index) }
                isSafe(modifiedNumbers)
            }
        }
    }

    // Read the test input from the `src/Day02_test.txt` file.
    val testInput = readInput("Day02_test")

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")

    part1(input).println()
    part2(input).println()
}
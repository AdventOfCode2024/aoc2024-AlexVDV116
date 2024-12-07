private val DIRECTIONS = listOf(
    0 to 1,    // Right
    1 to 0,    // Down
    1 to 1,    // Diagonal Down-Right
    1 to -1,   // Diagonal Down-Left
    0 to -1,   // Left
    -1 to 0,   // Up
    -1 to -1,  // Diagonal Up-Left
    -1 to 1    // Diagonal Up-Right
)

private const val WORD = "XMAS"

fun main() {
    // Read the test input and input files
    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    // Create a 2D grid from the input
    fun createGrid(input: List<String>): Array<CharArray> =
        Array(input.size) { row -> input[row].toCharArray() }

    // Check if the word is present in the grid at the given position and direction
    fun isWordAt(grid: Array<CharArray>, word: String, startRow: Int, startCol: Int, direction: Pair<Int, Int>): Boolean {
        val (dRow, dCol) = direction
        val rows = grid.size
        val cols = grid[0].size

        for (i in word.indices) {
            val newRow = startRow + i * dRow
            val newCol = startCol + i * dCol
            if (newRow !in 0 until rows || newCol !in 0 until cols || grid[newRow][newCol] != word[i]) {
                return false
            }
        }

        return true
    }

    // Count the number of occurrences of the word in the grid
    fun countOccurrences(grid: Array<CharArray>, word: String): Int {
        var count = 0
        for ((rowIndex, row) in grid.withIndex()) {
            for ((colIndex, _) in row.withIndex()) {
                for (direction in DIRECTIONS) {
                    if (isWordAt(grid, word, rowIndex, colIndex, direction)) {
                        count++
                    }
                }
            }
        }
        return count
    }

    fun part1(input: List<String>): Int {
        val grid = createGrid(input)
        return countOccurrences(grid, WORD)
    }

    // Check if the characters are M and S in some order
    fun isMasOrSam(c1: Char, c2: Char): Boolean =
        (c1 == 'M' && c2 == 'S') || (c1 == 'S' && c2 == 'M')

    // Iterate over each cell that could be the center of the 3x3 pattern
    // If the center is 'A', check the diagonals to see if they form MAS or SAM
    fun countXMas(grid: Array<CharArray>): Int {
        val rows = grid.size
        if (rows == 0) return 0
        val cols = grid[0].size
        var count = 0

        // We need at least a 3x3 area to form the pattern
        // So we can skip the borders: start from row=1 to row=rows-2, col=1 to cols-2
        for (r in 1 until rows - 1) {
            for (c in 1 until cols - 1) {
                if (grid[r][c] == 'A') {
                    // Get each diagonal
                    val topLeft = grid.getOrNull(r - 1)?.getOrNull(c - 1)
                    val topRight = grid.getOrNull(r - 1)?.getOrNull(c + 1)
                    val bottomLeft = grid.getOrNull(r + 1)?.getOrNull(c - 1)
                    val bottomRight = grid.getOrNull(r + 1)?.getOrNull(c + 1)

                    if (topLeft != null && topRight != null && bottomLeft != null && bottomRight != null) {
                        // Check if the diagonals form MAS or SAM
                        val diagonal1Valid = isMasOrSam(topLeft, bottomRight)
                        val diagonal2Valid = isMasOrSam(topRight, bottomLeft)

                        if (diagonal1Valid && diagonal2Valid) count++
                    }
                }
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val grid = createGrid(input)
        return countXMas(grid)
    }

    // Print solutions
    println(part1(input))
    println(part2(input))
}
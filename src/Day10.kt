fun main() {
    fun moves(inputs: List<String>): List<Move> {
        val moves = inputs.map {
            Move(
                Direction.getDirectionBy(it.split(" ")[0]),
                it.split(" ")[1].toInt()
            )
        }
        return moves
    }

    fun part1(inputs: List<String>): Int {
        return 0
    }

    fun part2(inputs: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 0)
    check(part2(testInput) == 0)

    val input = readInput("Day10")
//    println(part1(input))
    check(part1(input) == 0)
//    println(part2(input))
//    check(part2(input) == 0)
}

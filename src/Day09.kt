import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {

    fun part1(inputs: List<String>): Int {
        return followPath(2, inputs)
    }

    fun part2(inputs: List<String>): Int {
        return followPath(10, inputs)
    }


// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 88)
    check(part2(testInput) == 36)

    val input = readInput("Day09")
//    println(part1(input))
    check(part1(input) == 6181)
//    println(part2(input))
    check(part2(input) == 2386)
}

data class Point(val x: Int = 0, val y: Int = 0) {
    fun move(direction: Char): Point =
        when (direction) {
            'U' -> copy(y = y - 1)
            'D' -> copy(y = y + 1)
            'L' -> copy(x = x - 1)
            'R' -> copy(x = x + 1)
            else -> throw IllegalArgumentException("Unknown Direction: $direction")
        }

    fun moveTowards(other: Point): Point =
        Point(
            (other.x - x).sign + x,
            (other.y - y).sign + y
        )

    fun touches(other: Point): Boolean =
        (x - other.x).absoluteValue <= 1 && (y - other.y).absoluteValue <= 1

}

fun followPath(knots: Int, inputs: List<String>): Int {
    val headPath: String = parseInputDay9(inputs)
    val rope = Array(knots) { Point() }
    val tailVisits = mutableSetOf(Point())

    headPath.forEach { direction ->
        rope[0] = rope[0].move(direction)
        rope.indices.windowed(2, 1) { (head, tail) ->
            if (!rope[head].touches(rope[tail])) {
                rope[tail] = rope[tail].moveTowards(rope[head])
            }
        }
        tailVisits += rope.last()
    }
    return tailVisits.size
}


fun parseInputDay9(input: List<String>): String =
    input.joinToString("") { row ->
        val direction = row.substringBefore(" ")
        val numberOfMoves = row.substringAfter(' ').toInt()
        direction.repeat(numberOfMoves)
    }

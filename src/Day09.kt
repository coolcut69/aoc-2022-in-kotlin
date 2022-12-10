import Direction.*
import kotlin.math.abs

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
        val moves = moves(inputs)
        val head = Head(0, 0)
        val tail = Tail(0, 0)
        val allPositions: MutableSet<Position> = LinkedHashSet()
        allPositions.add(Position(tail.x, tail.y))

        for (move in moves) {
            repeat(move.steps) {
                head.moveDirection(move.direction)
                if (tail.toFarFrom(head)) {
                    tail.moveDirection(move.direction, head)
                    allPositions.add(Position(tail.x, tail.y))
                }
            }
        }
        return allPositions.size
    }

    fun part2(inputs: List<String>): Int {
        val moves = moves(inputs)
        val tail = Tail(0, 0)
        val snake = MutableList(10) { Head(0, 0) }
        val allPositions: MutableSet<Position> = LinkedHashSet()
        allPositions.add(Position(tail.x, tail.y))
        for (move in moves) {
            repeat(move.steps) {
                snake[0].moveDirection(move.direction)
                if (tail.toFarFrom(snake[0])) {
                    tail.moveDirection(move.direction, snake[0])
                    allPositions.add(Position(tail.x, tail.y))
                }
            }
        }
        return allPositions.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 13)

    val input = readInput("Day09")
//    println(part1(input))
    check(part1(input) == 6181)
//    println(part2(input))
//    check(part2(input) == 0)
}

data class Move(val direction: Direction, val steps: Int)

data class Head(var x: Int, var y: Int) {
    fun moveDirection(direction: Direction) {
        when (direction) {
            RIGHT -> x++
            UP -> y++
            LEFT -> x--
            DOWN -> y--
        }
    }
}

data class Tail(var x: Int, var y: Int) {
    private fun moveTo(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    fun toFarFrom(head: Head): Boolean {
        return (abs(head.x - this.x) > 1 || abs(head.y - this.y) > 1)
    }

    fun moveDirection(direction: Direction, head: Head) = when (direction) {
        RIGHT -> moveTo(head.x - 1, head.y)
        UP -> moveTo(head.x, head.y - 1)
        LEFT -> moveTo(head.x + 1, head.y)
        DOWN -> moveTo(head.x, head.y + 1)
    }
}

data class Position(var x: Int, var y: Int)

enum class Direction {
    RIGHT, UP, LEFT, DOWN;

    companion object {
        fun getDirectionBy(name: String): Direction = when (name) {
            "R" -> RIGHT
            "U" -> UP
            "L" -> LEFT
            "D" -> DOWN
            else ->
                throw IllegalArgumentException("unknown value")
        }
    }
}




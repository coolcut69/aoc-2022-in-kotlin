import kotlin.math.abs

fun main() {
    fun part1(inputs: List<String>): Int {
        val moves = inputs.map {
            Move(
                Direction.getDirectionBy(it.split(" ")[0]),
                it.split(" ")[1].toInt()
            )
        }
        val head = Head(0, 0)
        for (move in moves) {
            head.move(move)
        }
        return head.tail().positions().size
    }

    fun part2(inputs: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
//    check(part2(testInput) == 0)

    val input = readInput("Day09")
//    println(part1(input))
    check(part1(input) == 6181)
//    println(part2(input))
//    check(part2(input) == 0)
}

data class Move(val direction: Direction, val steps: Int)

data class Head(var x: Int, var y: Int) {

    private val tail = Tail(0, 0)

    fun tail(): Tail {
        return this.tail
    }

    fun move(move: Move) {
        when (move.direction) {
            Direction.RIGHT -> {
                repeat(move.steps) {
                    x++
                    if (tail.toFarFromHead(x, y)) {
                        tail.moveTo(this.x - 1, this.y)
                    }
                }
            }

            Direction.UP -> {
                repeat(move.steps) {
                    y++
                    if (tail.toFarFromHead(x, y)) {
                        tail.moveTo(this.x, this.y - 1)
                    }
                }
            }

            Direction.LEFT -> {
                repeat(move.steps) {
                    x--
                    if (tail.toFarFromHead(x, y)) {
                        tail.moveTo(this.x + 1, this.y)
                    }
                }
            }

            Direction.DOWN -> {
                repeat(move.steps) {
                    y--
                    if (tail.toFarFromHead(x, y)) {
                        tail.moveTo(this.x, this.y + 1)
                    }
                }
            }
        }
    }
}

data class Tail(var x: Int, var y: Int) {
    private val allPositions: MutableSet<Position> = LinkedHashSet()

    fun positions(): MutableSet<Position> {
        return this.allPositions
    }

    fun toFarFromHead(x: Int, y: Int): Boolean {
        return (abs(x - this.x) > 1 || abs(y - this.y) > 1)
    }

    init {
        x = 0
        y = 0
        this.allPositions.add(Position(this.x, this.y))
    }

    fun moveTo(x: Int, y: Int) {
        this.x = x
        this.y = y
        this.allPositions.add(Position(this.x, this.y))
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




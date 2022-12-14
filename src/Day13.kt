import com.beust.klaxon.JsonArray
import com.beust.klaxon.Parser.Companion.default

private val JSON_PARSER = default()

fun main() {

    fun part1(inputs: List<String>): Int {
        var sum = 0
        repeat((inputs.size + 1) / 3) { index ->
            val x = index * 3
            val left = inputs[x + 0]
            val right = inputs[x + 1]
            println("$left vs $right")

            val packet1 = JSON_PARSER.parse(StringBuilder(left)) as JsonArray<*>
            val packet2 = JSON_PARSER.parse(StringBuilder(right)) as JsonArray<*>

            sum += if (packet1 compareTo packet2 < 0) index + 1 else 0
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 13)
//    check(part2(testInput) == 0)

    val input = readInput("Day13")
    println(part1(input))
    check(part1(input) == 6070)

//    println(part2(input))
//    check(part2(input) == 0)
}


private infix fun Any.compareTo(other: Any): Int {
    if (this.toString().toIntOrNull() != null && other.toString().toIntOrNull() != null) {
        return this.toString().toInt().compareTo(other.toString().toInt())
    }

    if (this is JsonArray<*> || other is JsonArray<*>) {
        // Wrap in list if single element
        val newThis = if (this is JsonArray<*>) this else JsonArray(this)
        val newOther = if (other is JsonArray<*>) other else JsonArray(other)

        for (i in newThis.indices) {
            if (i in newOther.indices) {
                val comparison = newThis[i]!! compareTo newOther[i]!!
                if (comparison == 0) {
                    continue
                }
                return comparison
            } else {
                return 1         // Left side is longer
            }
        }

        return if (newThis.size == newOther.size) 0 else -1     // Equal size or right side is longer
    }

    return 0
}

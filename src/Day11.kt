import java.math.BigInteger

fun main() {
    fun extracted(inputs: List<String>, boredFactor: Int = 3): List<Monkey> {
        val monkeys: MutableList<Monkey> = ArrayList()

        for (i in 0..inputs.size / 7) {
            val monkeyId = i * 7
            val numbers: List<String> = inputs[monkeyId + 1].substringAfter("Starting items: ").split(", ")
            val operation = inputs[monkeyId + 2].substringAfter("Operation: new = ")
            val divide = inputs[monkeyId + 3].substringAfter("Test: divisible by ").toInt()
            val trueMonkey = inputs[monkeyId + 4].substringAfter("If true: throw to monkey ").toInt()
            val falseMonkey = inputs[monkeyId + 5].substringAfter("If false: throw to monkey ").toInt()
            monkeys.add(
                Monkey(
                    divide,
                    trueMonkey,
                    falseMonkey,
                    numbers.map { it.toLong() }.toMutableList(),
                    operation,
                    boredFactor
                )
            )
        }
        return monkeys
    }

    fun magicNumber(inputs: List<String>): Int {
        var magicNumber = 1;
        for (i in 0..inputs.size / 7) {
            val monkeyId = i * 7
            magicNumber *= inputs[monkeyId + 3].substringAfter("Test: divisible by ").toInt()
        }
        return magicNumber
    }


    fun part1(inputs: List<String>, magicNumber: Int): Long {
        val monkeys = extracted(inputs)

        repeat(20) {
            for (monkey in monkeys) {
                val passes = monkey.playRound(magicNumber)
                for (pas in passes) {
                    val m = monkeys.get(pas.monkey)
                    m.addItem(pas.level)
                }
            }
        }
        val sorted = monkeys.map { it.inspections() }.sorted().reversed()
        return sorted[0] * sorted[1]
    }

    fun part2(inputs: List<String>, magicNumber: Int): BigInteger {
        val monkeys = extracted(inputs, 1)

        repeat(10000) { index ->
            for (monkey in monkeys) {
                val passes = monkey.playRound(magicNumber)
                for (pas in passes) {
                    val m = monkeys.get(pas.monkey)
                    m.addItem(pas.level)
                }
            }
        }
        val sorted = monkeys.map { it.inspections() }.sorted().reversed()
        return BigInteger.valueOf(sorted[0]).multiply(BigInteger.valueOf(sorted[1]))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput, magicNumber(testInput)) == 10605L)
    check(part2(testInput, magicNumber(testInput)) == BigInteger.valueOf(2713310158L))

    val input = readInput("Day11")
    println(part1(input, magicNumber(input)))
    check(part1(input, magicNumber(input)) == 66124L)
    println(part2(input, magicNumber(input)))
    check(part2(input, magicNumber(input)) == BigInteger.valueOf(19309892877L))
}

data class Monkey(
    val divisionTest: Int,
    val testTrue: Int,
    val testFalse: Int,
    var items: MutableList<Long>,
    val operation: String,
    val borredFactor: Int = 3
) {

    private var inspections: Long = 0

    fun playRound(i: Int): List<Pass> {
        val passes: MutableList<Pass> = ArrayList()
        for (item in items) {
            inspections++
            var new = applyOperation(item) / borredFactor

            new %= i

            if (new % divisionTest == 0L) {
                passes.add(Pass(testTrue, new))
            } else {
                passes.add(Pass(testFalse, new))
            }
        }
        items = ArrayList()
        return passes
    }

    fun inspections(): Long {
        return inspections
    }

    private fun applyOperation(old: Long): Long {
        val s = operation.substringAfter("old ")
        val op = s.split(" ")[0]
        val otherValue = s.split(" ")[1]
        return when (op) {
            "+" -> old + getOldValue(old, otherValue)
            "*" -> old * getOldValue(old, otherValue)
            else -> throw IllegalArgumentException("unknown operator")
        }
    }

    private fun getOldValue(old: Long, other: String): Long {
        return if (other == "old") {
            old
        } else {
            other.toLong()
        }
    }

    fun addItem(level: Long) {
        this.items.add(level)
    }

}

data class Pass(val monkey: Int, val level: Long)



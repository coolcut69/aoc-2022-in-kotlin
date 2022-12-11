fun main() {
    fun extracted(inputs: List<String>, borredFactor: Int = 3): List<Monkey> {
        val monkeys: MutableList<Monkey> = ArrayList()

        for (i in 0..inputs.size / 7) {
            val monkeyId = i * 7
            val numbers: List<String> = inputs[monkeyId + 1].substringAfter("Starting items: ").split(", ")
            val operation = inputs[monkeyId + 2].substringAfter("Operation: new = ")
            val divid = inputs[monkeyId + 3].substringAfter("Test: divisible by ").toInt()
            val trueMonkey = inputs[monkeyId + 4].substringAfter("If true: throw to monkey ").toInt()
            val falseMonkey = inputs[monkeyId + 5].substringAfter("If false: throw to monkey ").toInt()
            monkeys.add(Monkey(divid, trueMonkey, falseMonkey, numbers.map { it.toLong() }.toMutableList(), operation, borredFactor))
        }
        return monkeys
    }

    fun part1(inputs: List<String>): Long {
        val monkeys = extracted(inputs)

        repeat(20) {
            for (monkey in monkeys) {
                val passes = monkey.playRound()
                for (pas in passes) {
                    val m = monkeys.get(pas.monkey)
                    m.addItem(pas.level)
                }
            }
        }
        val sorted = monkeys.map { it.inspections() }.sorted().reversed()
        return sorted[0] * sorted[1]
    }

    fun part2(inputs: List<String>): Long {
        val monkeys = extracted(inputs, 1)

        repeat(10000) {
            for (monkey in monkeys) {
                val passes = monkey.playRound()
                for (pas in passes) {
                    val m = monkeys.get(pas.monkey)
                    m.addItem(pas.level)
                }
            }
        }
        val sorted = monkeys.map { it.inspections() }.sorted().reversed()
        return sorted[0] * sorted[1]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605L)
//    check(part2(testInput) == 2713310158)

    val input = readInput("Day11")
    println(part1(input))
//    check(part1(input) == 0)
    println(part2(input))
//    check(part2(input) == 0)
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

    fun playRound(): List<Pass> {
        val passes: MutableList<Pass> = ArrayList()
        for (item in items) {
            inspections++
            val new = applyOperation(item) / borredFactor
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

    fun applyOperation(old: Long): Long {
        val s = operation.substringAfter("old ")
        val op = s.split(" ")[0]
        val otherValue = s.split(" ")[1]
        when (op) {
            "+" -> return old + getOldValue(old, otherValue)
            "*" -> return old * getOldValue(old, otherValue)
            else -> throw IllegalArgumentException("unknown operator")
        }
    }

    fun getOldValue(old: Long, other: String): Long {
        if (other.equals("old")) {
            return old
        } else {
            return other.toLong()
        }
    }

    fun addItem(level: Long) {
        this.items.add(level)
    }

}

data class Pass(val monkey: Int, val level: Long)

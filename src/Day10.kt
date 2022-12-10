fun main() {

    fun createRegister(inputs: List<String>): Register {
        val register = Register()
        for (s in inputs) {
            if (s == "noop") {
                register.addIntruction(Instruction("noop", 0))
            } else {
                register.addIntruction(Instruction("addx", s.split(" ")[1].toInt()))
            }
        }
        return register
    }

    fun part1(inputs: List<String>): Int {
        val register = createRegister(inputs)

        return register.valueAt(20) * 20 +
                register.valueAt(60) * 60 +
                register.valueAt(100) * 100 +
                register.valueAt(140) * 140 +
                register.valueAt(180) * 180 +
                register.valueAt(220) * 220

    }

    fun part2(inputs: List<String>): Int {
        val register = createRegister(inputs)

        for (i in 0..200 step 40) {
            var display = ""
            for (cycle in i..i + 39) {
                val valueAt = register.valueAt(cycle + 1)
                if (cycle - i == valueAt || cycle - i == valueAt + 1 || cycle - i == valueAt - 1) {
                    display += "#"
                } else {
                    display += "."
                }
            }
            println(display)
        }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    check(part2(testInput) == 0)

    val input = readInput("Day10")
//    println(part1(input))
    check(part1(input) == 15680)
    println(part2(input))
//    check(part2(input) == 0)
}

class Register() {
    private val instructions: MutableList<Instruction> = ArrayList()
    private val registerX: MutableList<Int> = ArrayList()

    init {
        registerX.add(1)
    }

    fun xValue(): List<Int> {
        return registerX
    }

    fun valueAt(pos: Int): Int {
        return registerX.subList(0, pos).sum()
    }

    fun addIntruction(instruction: Instruction) {
        this.instructions.add(instruction)

        if (instruction.action == "noop") {
            registerX.add(0)
        } else {
            registerX.add(0)
            registerX.add(instruction.number)
        }
    }
}

data class Instruction(val action: String, val number: Int)

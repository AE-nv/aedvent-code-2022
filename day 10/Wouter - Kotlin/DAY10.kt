package days

import AOCAssignmentReader
import DataStructureHelper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.math.abs

class DAY10 {
    val day = 10
    val eo1 = 13140
    val eo2 = 0
    val ei: String = """
        addx 15
        addx -11
        addx 6
        addx -3
        addx 5
        addx -1
        addx -8
        addx 13
        addx 4
        noop
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx -35
        addx 1
        addx 24
        addx -19
        addx 1
        addx 16
        addx -11
        noop
        noop
        addx 21
        addx -15
        noop
        noop
        addx -3
        addx 9
        addx 1
        addx -3
        addx 8
        addx 1
        addx 5
        noop
        noop
        noop
        noop
        noop
        addx -36
        noop
        addx 1
        addx 7
        noop
        noop
        noop
        addx 2
        addx 6
        noop
        noop
        noop
        noop
        noop
        addx 1
        noop
        noop
        addx 7
        addx 1
        noop
        addx -13
        addx 13
        addx 7
        noop
        addx 1
        addx -33
        noop
        noop
        noop
        addx 2
        noop
        noop
        noop
        addx 8
        noop
        addx -1
        addx 2
        addx 1
        noop
        addx 17
        addx -9
        addx 1
        addx 1
        addx -3
        addx 11
        noop
        noop
        addx 1
        noop
        addx 1
        noop
        noop
        addx -13
        addx -19
        addx 1
        addx 3
        addx 26
        addx -30
        addx 12
        addx -1
        addx 3
        addx 1
        noop
        noop
        noop
        addx -9
        addx 18
        addx 1
        addx 2
        noop
        noop
        addx 9
        noop
        noop
        noop
        addx -1
        addx 2
        addx -37
        addx 1
        addx 3
        noop
        addx 15
        addx -21
        addx 22
        addx -6
        addx 1
        noop
        addx 2
        addx 1
        noop
        addx -10
        noop
        noop
        addx 20
        addx 1
        addx 2
        addx 2
        addx -6
        addx -11
        noop
        noop
        noop
    """.trimIndent()

    val dh: DataStructureHelper = DataStructureHelper()

    data class CPU(val cycle:Int, val registerX: Int, val energy: Int) {
        fun executeInstruction(instruction: String) = when {
            instruction.startsWith("addx") -> increaseCycle().increaseCycle().addx(instruction.split(" ")[1].toInt())
            else -> noop()
        }

        private fun addx(amount: Int): CPU = CPU(cycle, registerX + amount, energy)
        private fun noop() = increaseCycle()
        private fun increaseCycle(): CPU {
            val newCycle = cycle + 1
            var newEnergy = energy
            if(listOf(20,60,100,140,180,220).contains(newCycle)) newEnergy = energy + (newCycle*registerX)

            if(abs(abs(registerX) - (cycle % 40)) <= 1 ) {
                print("#")
            } else {
                print(".")
            }
            if (newCycle % 40 == 0) println()
            return CPU(newCycle, registerX, newEnergy)
        }
    }

    fun implementP1(input: String): Int {
        return dh.lines(input).fold(CPU(0, 1, 0)) { cpu, line -> cpu.executeInstruction(line) }.energy
    }


    fun implementP2(input: String): Int {
        dh.lines(input).fold(CPU(0, 1, 0)) { cpu, line -> cpu.executeInstruction(line) }
        return 0
    }

    @Test
    fun runp1() {
        Assertions.assertThat(implementP1(ei)).isEqualTo(eo1)
        println(implementP1(AOCAssignmentReader().readInputForDay(day)))
    }

    @Test
    fun runp2() {
        implementP2(AOCAssignmentReader().readInputForDay(day))
    }
}
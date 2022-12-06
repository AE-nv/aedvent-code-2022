package days

import AOCAssignmentReader
import DataStructureHelper
import GridViewer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.awt.EventQueue

class DAY06 {
    val day = 6
    val eo1 = 7
    val eo2 = 19
    val ei: String = """
        mjqjpqmgbljsphdztnvjfqwrcgsmlb
    """.trimIndent()

    fun implementP1(input: String): Int {
        return input.toList().windowed(4, 1).mapIndexed { pos, arr -> Pair(pos, allDifferent(arr)) }.first { it.second }.first + 4
    }

    fun allDifferent(s :List<Char>) = s.toSet().size==s.size

    fun implementP2(input: String): Int {
        return input.toList().windowed(14, 1).mapIndexed { pos, arr -> Pair(pos, allDifferent(arr)) }.first { it.second }.first + 14

    }

    @Test
    fun runp1() {
        assertThat(implementP1(ei)).isEqualTo(eo1)
        println(implementP1(AOCAssignmentReader().readInputForDay(day)))
    }

    @Test
    fun runp2() {
        assertThat(implementP2(ei)).isEqualTo(eo2)
        println(implementP2(AOCAssignmentReader().readInputForDay(day)))
    }

}
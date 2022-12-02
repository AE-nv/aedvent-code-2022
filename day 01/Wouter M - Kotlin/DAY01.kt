package days

import AOCAssignmentReader
import DataStructureHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DAY01 {
    val eo1 = 24000
    val eo2 = 45000
    val ei: String = """
        1000
        2000
        3000
        
        4000
        
        5000
        6000
        
        7000
        8000
        9000
        
        10000
    """.trimIndent()

    val dh: DataStructureHelper = DataStructureHelper()

    fun implementP1(input: String): Int {
        val lines = dh.lines(input)
        return lines.fold(listOf(0)) { acc, value -> if (value.isBlank()) acc + 0 else acc.dropLast(1) + (acc[acc.size - 1] + value.toInt()) }.maxOf { it }
    }


    fun implementP2(input: String): Int {
        val lines = dh.lines(input)
        return lines.fold(listOf(0))
        { acc, value -> if (value.isBlank()) acc + 0 else acc.dropLast(1) + (acc[acc.size - 1] + value.toInt()) }
            .sortedDescending().take(3).sum()
    }


    @Test
    fun runp1() {
        assertThat(implementP1(ei)).isEqualTo(eo1.toLong())
        println(implementP1(AOCAssignmentReader().readInputForDay(1)))
    }

    @Test
    fun runp2() {
        assertThat(implementP2(ei)).isEqualTo(eo2.toLong())
        println(implementP2(AOCAssignmentReader().readInputForDay(1)))
    }
}
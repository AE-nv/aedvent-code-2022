package days

import AOCAssignmentReader
import DataStructureHelper
import GridViewer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.awt.EventQueue

class DAY04 {
    val day = 4
    val eo1 = 2
    val eo2 = 4
    val ei: String = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent()

    val dh: DataStructureHelper = DataStructureHelper()

    fun split(input: String): List<Int> {
        return (input.split("-")[0].toInt()..input.split("-")[1].toInt()).toList()
    }

    fun implementP1(input: String): Int {
        return dh.lines(input).map { listOf(split(it.split(",")[0]), split(it.split(",")[1])) }.filter { it[0].containsAll(it[1]) || it[1].containsAll(it[0]) }.size
    }


    fun implementP2(input: String): Int {
        return dh.lines(input).map { listOf(split(it.split(",")[0]), split(it.split(",")[1])) }.filter { it[0].any { _it -> it[1].contains(_it)  } }.size
    }

    @Test
    fun runp1() {
        assertThat(implementP1(ei)).isEqualTo(eo1.toLong())
        println(implementP1(AOCAssignmentReader().readInputForDay(day)))
    }

    @Test
    fun runp2() {
        assertThat(implementP2(ei)).isEqualTo(eo2.toLong())
        println(implementP2(AOCAssignmentReader().readInputForDay(day)))
    }

}
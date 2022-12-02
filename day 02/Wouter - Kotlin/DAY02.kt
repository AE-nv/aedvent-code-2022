package days

import AOCAssignmentReader
import DataStructureHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DAY02 {
    val day = 2
    val eo1 = 15
    val eo2 = 12
    val ei: String = """
        A Y
        B X
        C Z
    """.trimIndent()

    val dh: DataStructureHelper = DataStructureHelper()

    fun implementP1(input: String): Int {
        return dh.lines(input).filter { it.isNotBlank() }.map { points(it[0].toString(), it[2].toString()) }.sum()
    }


    fun implementP2(input: String): Int {
        return dh.lines(input).filter { it.isNotBlank() }.map { points2(it[0].toString(), it[2].toString()) } .sum()
    }

    fun points(input1: String, input2: String) = when {
        input1 == "A" && input2 == "X" -> 1 + 3
        input1 == "A" && input2 == "Y" -> 2 + 6
        input1 == "A" && input2 == "Z" -> 3 + 0
        input1 == "B" && input2 == "X" -> 1 + 0
        input1 == "B" && input2 == "Y" -> 2 + 3
        input1 == "B" && input2 == "Z" -> 3 + 6
        input1 == "C" && input2 == "X" -> 1 + 6
        input1 == "C" && input2 == "Y" -> 2 + 0
        input1 == "C" && input2 == "Z" -> 3 + 3

        else -> -1
    }

    fun points2(input1: String, input2: String) = when {
        input1 == "A" && input2 == "X" -> points(input1, "Z")
        input1 == "A" && input2 == "Y" -> points(input1, "X")
        input1 == "A" && input2 == "Z" -> points(input1, "Y")
        input1 == "B" && input2 == "X" -> points(input1, "X")
        input1 == "B" && input2 == "Y" -> points(input1, "Y")
        input1 == "B" && input2 == "Z" -> points(input1, "Z")
        input1 == "C" && input2 == "X" -> points(input1, "Y")
        input1 == "C" && input2 == "Y" -> points(input1, "Z")
        input1 == "C" && input2 == "Z" -> points(input1, "X")

        else -> -1
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
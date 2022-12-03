package days

import AOCAssignmentReader
import DataStructureHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DAY03 {
    val day = 3
    val eo1 = 157
    val eo2 = 70
    val ei: String = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent()

    val dh: DataStructureHelper = DataStructureHelper()

    fun implementP1(input: String): Int {
        return dh.lines(input).map { it.substring(0,it.length/2).toSet().intersect(it.substring(it.length/2,it.length).toSet()).first() }
            .map {it.code - 96} .map {if (it < 0) it + 96 - 38 else it}.sum()
    }


    fun implementP2(input: String): Int {
        return dh.lines(input).asSequence().windowed(3,3).map{ it[0].toSet().intersect(it[1].toSet()).intersect(it[2].toSet()).first()}.map {it.code - 96} .map {if (it < 0) it + 96 - 38 else it}.sum()
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
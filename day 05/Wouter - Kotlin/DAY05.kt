package days

import AOCAssignmentReader
import DataStructureHelper
import GridViewer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.awt.EventQueue

class DAY05 {
    val day = 5
    val eo1 = "CMZ"
    val eo2 = "MCD"
    val ei: String = """
            [D]    
        [N] [C]    
        [Z] [M] [P]
         1   2   3 
        
        move 1 from 2 to 1
        move 3 from 1 to 3
        move 2 from 2 to 1
        move 1 from 1 to 2
    """.trimIndent()

    val dh: DataStructureHelper = DataStructureHelper()

    fun readArrays(input: String): List<List<String>> {
        val lines = dh.lines(input).takeWhile { it.contains("[") }. map { Regex("( {4}|[A-Z]+)").findAll(it).map {_it -> _it.value.trim() } } . map { it.toList()}
        return lines.fold<List<String>, List<List<String>>>((1..lines[0].size).map { listOf() }) { acc, s -> s.mapIndexed { pos, input -> acc[pos] + input} } .map { it.filter { s -> s.isNotBlank() } }
    }

    data class Command(val amount: Int, val from: Int, val to: Int)

    fun readCommands(input: String): List<Command> {
        return dh.lines(input).filter { it.contains("move") }. map { Regex("(\\d+)").findAll(it).toList().map {_it -> _it.value.trim().toInt() } }. map { Command(it[0], it[1], it[2]) }
    }

    fun implementP1(input: String): String {
        return readCommands(input).fold(readArrays(input)) {acc, command -> acc.mapIndexed {pos, list -> when(pos) {
            command.from-1 -> list.subList(command.amount, list.size)
            command.to-1 -> acc[command.from-1].take(command.amount).reversed().plus(list)
            else -> list
        } } }.fold("") {acc, strings -> acc+strings.first() }
    }


    fun implementP2(input: String): String {
        return readCommands(input).fold(readArrays(input)) {acc, command -> acc.mapIndexed {pos, list -> when(pos) {
            command.from-1 -> list.subList(command.amount, list.size)
            command.to-1 -> acc[command.from-1].take(command.amount).plus(list)
            else -> list
        } } }.fold("") {acc, strings -> acc+strings.first() }
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
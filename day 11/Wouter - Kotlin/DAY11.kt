package days

import AOCAssignmentReader
import DataStructureHelper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger
import kotlin.script.experimental.jsr223.KotlinJsr223DefaultScriptEngineFactory

class DAY11 {
    val day = 11
    val eo1 = 10605L
    val eo2 = 2713310158L
    val ei: String = """
        Monkey 0:
          Starting items: 79, 98
          Operation: new = old * 19
          Test: divisible by 23
            If true: throw to monkey 2
            If false: throw to monkey 3
        
        Monkey 1:
          Starting items: 54, 65, 75, 74
          Operation: new = old + 6
          Test: divisible by 19
            If true: throw to monkey 2
            If false: throw to monkey 0
        
        Monkey 2:
          Starting items: 79, 60, 97
          Operation: new = old * old
          Test: divisible by 13
            If true: throw to monkey 1
            If false: throw to monkey 3
        
        Monkey 3:
          Starting items: 74
          Operation: new = old + 3
          Test: divisible by 17
            If true: throw to monkey 0
            If false: throw to monkey 1
    """.trimIndent()

    val dh: DataStructureHelper = DataStructureHelper()

    data class Item(val worryLevel: BigInteger) {
        // part1
        // fun relief() = Item(worryLevel / 3)
        // part2
         fun relief() = Item(worryLevel)
    }

    data class Throw(val Item: Item, val toMonkey: Int)
    data class Monkey(
        val items: List<Item>,
        val operation: (BigInteger) -> BigInteger,
        val testNumber: Int,
        val test: (BigInteger) -> Boolean,
        val monkeyIfTrue: Int,
        val monkeyIfFalse: Int,
        val amountOfInspections: Long
    ) {
        private fun inspect(item: Item, gcd: Int) = Item(operation(item.worryLevel).mod(gcd.toBigInteger())).relief()

        fun addInspectionCount(inspections: Long) = Monkey(items, operation,testNumber, test, monkeyIfTrue, monkeyIfFalse, amountOfInspections + inspections)
        fun round(gcd: Int) = items.map { inspect(it, gcd) }.map { Throw(it, if (test(it.worryLevel)) monkeyIfTrue else monkeyIfFalse) }

        fun receiveItem(item: Item) = Monkey(items + item, operation,testNumber, test, monkeyIfTrue, monkeyIfFalse, amountOfInspections)
        fun clearItems() = Monkey(emptyList(), operation,testNumber, test, monkeyIfTrue, monkeyIfFalse, amountOfInspections)
    }

    data class Forrest(val monkeys: ArrayList<Monkey>) {
        var gcd = 1
        fun addMonkey(monkey: Monkey) {
            monkeys.add(monkey)
            gcd = monkeys.fold(1) {acc, m -> acc * m.testNumber }
        }

        fun playRound() {
            monkeys.forEachIndexed { index, monkey ->
                val throws = monkey.round(gcd)
                throws.forEach { monkeys[it.toMonkey] = monkeys[it.toMonkey].receiveItem(it.Item) }
                monkeys[index] = monkeys[index].addInspectionCount(throws.size.toLong())
                monkeys[index] = monkeys[index].clearItems()
            }
        }
    }

    fun isDivisible(x: BigInteger, y: BigInteger) = (x / y * y).compareTo(x) == 0

fun monkeyParser(lines: List<String>): Monkey {
    val items = lines[1].replace("  Starting items: ", "").split(", ").map { Item(it.toBigInteger()) }
    val operation = createFunction(lines[2].replace("  Operation: new = ", ""))
    val testNumber = lines[3].replace("  Test: divisible by ", "").toInt()
    val test = { worry: BigInteger -> isDivisible(worry,testNumber.toBigInteger())  }
    val ifTrue = lines[4].replace("    If true: throw to monkey ", "").toInt()
    val ifFalse = lines[5].replace("    If false: throw to monkey ", "").toInt()
    return Monkey(items, operation, testNumber, test, ifTrue, ifFalse, 0)
}

fun createFunction(script: String): (BigInteger) -> BigInteger {
    val engine = KotlinJsr223DefaultScriptEngineFactory().scriptEngine;
    val bigIntScript = script.replace(Regex("\\d+")) { matchResult -> "BigInteger(\"${matchResult.value}\")" }
    val function = engine.eval("import java.math.BigInteger; val x: (BigInteger) -> BigInteger = { old -> $bigIntScript }; x")
    return (function as (BigInteger) -> BigInteger)
}

fun implementP1(input: String): Long {
    val forrest = Forrest(arrayListOf())
    dh.lines(input).chunked(6).map { monkeyParser(it) }.forEach { forrest.addMonkey(it) }
    (1..20).forEach { forrest.playRound() }
    return forrest.monkeys.sortedBy { it.amountOfInspections }.reversed().take(2).map { it.amountOfInspections }.fold(1) { acc, i -> acc * i }
}


fun implementP2(input: String): Long {
    val forrest = Forrest(arrayListOf())
    dh.lines(input).chunked(6).map { monkeyParser(it) }.forEach { forrest.addMonkey(it) }
    (1..10000).forEach { forrest.playRound() }
    forrest.monkeys.forEach { println(it.amountOfInspections) }
    return forrest.monkeys.sortedBy { it.amountOfInspections }.reversed().take(2).map { it.amountOfInspections }.fold(1) { acc, i -> acc * i }
}

@Test
fun runp1() {
    Assertions.assertThat(implementP1(ei)).isEqualTo(eo1)
    println(implementP1(AOCAssignmentReader().readInputForDay(day)))

}

@Test
fun runp2() {
    Assertions.assertThat(implementP2(ei)).isEqualTo(eo2)
    println(implementP2(AOCAssignmentReader().readInputForDay(day)))
}
}
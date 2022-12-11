import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.Util.*
import scala.collection.immutable.Map
import scala.collection.immutable.Nil.head
import scala.collection.mutable
import scala.collection.mutable.HashSet
import scala.io.Source.fromResource

class Day11 {

  class Monkey {

    var id: Int = -1
    val itemQueue: mutable.Queue[Long] = new mutable.Queue()
    var numInspections: Long = 0
    var operation: Long => Long = i => i
    var test: Long = -1
    var trueReceiver: Int = -1
    var falseReceiver: Int = -1

    def receiveItem(item: Long): Unit = itemQueue.addOne(item)

    def receiveItems(items: Array[Long]): Unit = itemQueue.addAll(items)

    def setOperation(operator: String, operand: String): Unit = {
      operation = operator match {
        case "*" => i => i * (if (operand == "old") i else operand.toLong)
        case "+" => i => i + (if (operand == "old") i else operand.toLong)
      }
    }

    def throwItems(monkeys: Map[Int, Monkey], divide: Boolean, smallestCommonDivisor: Long): Unit = {
      while(itemQueue.nonEmpty)
        throwItem(updateWorry(itemQueue.dequeue(), divide, smallestCommonDivisor), monkeys)
    }

    def updateWorry(i: Long, divide: Boolean, smallestCommonDivisor: Long): Long = {
      numInspections += 1
      if (divide) operation.apply(i) / 3
      else operation.apply(i) % smallestCommonDivisor
    }

    def throwItem(item: Long, monkeys: Map[Int, Monkey]): Unit = {
      if(item % test == 0L) monkeys(trueReceiver).receiveItem(item)
      else monkeys(falseReceiver).receiveItem(item)
    }
  }

  def run(input: List[String], rounds: Int = 20, divide: Boolean = true): Long = {
    val monkeys = parse(input)
    (1 to rounds).foreach(round => playRound(monkeys, divide, monkeys.map(_._2.test).product))

    monkeys.map(_._2.numInspections).toList.sorted.reverse.take(2).product
  }

  def playRound(monkeys: Map[Int, Monkey], divide: Boolean, smallestCommonDivisor: Long): Unit = {
    monkeys.toList.sortBy(_._1).foreach(monkey => monkey._2.throwItems(monkeys, divide, smallestCommonDivisor))
  }

  def parse(input: List[String]): Map[Int, Monkey] = {
    input.mkString("\n").split("\n\n").map(parseMonkey).toMap
  }

  def parseMonkey(input: String): (Int, Monkey) = {
    val monkey = new Monkey()
    val lines = input.split("\n")

    monkey.id = lines.head.dropRight(1).split(" ").last.toInt
    monkey.receiveItems(lines(1).split(":").last.trim.split(", ").map(_.toInt))

    val operationItems = lines(2).split(" ")
    val operand = operationItems.last
    monkey.setOperation(operationItems.dropRight(1).last, operand)
    monkey.test = lines(3).split(" ").last.toInt
    monkey.trueReceiver = lines(4).split(" ").last.toInt
    monkey.falseReceiver = lines(5).split(" ").last.toInt

    (monkey.id, monkey)
  }
}

class Day11Tests extends AnyFunSuite with Matchers {
  test("Example 1") {
    new Day11().run(fromResource("Day11example").getLines().toList) shouldBe 10605
  }

  test("Puzzle 1") {
    new Day11().run(fromResource("Day11").getLines().toList) shouldBe 55216
  }

  test("Example 2") {
    new Day11().run(fromResource("Day11example").getLines().toList, 10000, false) shouldBe 2713310158L
  }

  test("Puzzle 2") {
    new Day11().run(fromResource("Day11").getLines().toList, 10000, false) shouldBe 12848882750L
  }
}
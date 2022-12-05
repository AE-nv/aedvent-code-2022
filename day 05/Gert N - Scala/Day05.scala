import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.Util.*
import scala.collection.mutable
import scala.io.Source.fromResource
import scala.collection.mutable.Stack

object Day05 {

  type Move = (Int, Int, Int)
  type Stacks = List[mutable.Stack[Char]]

  def run(input: String, retainOrder: Boolean = false): String = {
    val (stacks, moves) = parse(input)
    val movedStacks = processMoves(moves, stacks, retainOrder)
    movedStacks.map(_.head).mkString
  }

  def parse(input: String): (Stacks, List[Move]) = {
    val split = input.split("\n\n")
    (parseStacks(split.head), parseMoves(split.last))
  }

  def parseStacks(input: String): Stacks = {
    val lines = input.split("\n")
    val numStacks = Integer.parseInt(lines.last.trim.last.toString)

    val transposed = lines.reverse.map(_.toArray)
      .mapInPlace(_.padTo(numStacks * 3 + numStacks - 1, ' '))
      .transpose

    val cleaned = transposed
      .filterNot(_.isEmpty)
      .filter(line => Character.isDigit(line.head.toString.head))

    cleaned.map(stack => mutable.Stack().pushAll(stack.drop(1).filterNot(c => c == ' '))).toList
  }

  def parseMoves(input: String): List[Move] = {
    input.split("\n").map(move =>
      val matches = "(\\d+)".r.findAllMatchIn(move).toList.map(m => Integer.parseInt(m.matched))
      (matches.head, matches(1) - 1, matches(2) - 1)
    ).toList
  }

  def processMoves(moves: List[Move], stacks: Stacks, retainOrder: Boolean): Stacks = {
    moves.foreach(move => {
      val taken = stacks(move._2).take(move._1)
      stacks(move._2).dropInPlace(move._1)

      if (retainOrder)
        stacks(move._3).pushAll(taken.reverse)
      else
        stacks(move._3).pushAll(taken)
    })

    stacks
  }
}

class Day05Tests extends AnyFunSuite with Matchers {
  test("Example 1") {
    val input =
      """    [D]
        |[N] [C]
        |[Z] [M] [P]
        | 1   2   3
        |
        |move 1 from 2 to 1
        |move 3 from 1 to 3
        |move 2 from 2 to 1
        |move 1 from 1 to 2""".stripMargin
    Day05.run(input) shouldBe "CMZ"
  }

  test("Puzzle 1") {
    Day05.run(fromResource("Day05").getLines().mkString("\n")) shouldBe "WCZTHTMPS"
  }

  test("Example 2") {
    val input =
      """    [D]
        |[N] [C]
        |[Z] [M] [P]
        | 1   2   3
        |
        |move 1 from 2 to 1
        |move 3 from 1 to 3
        |move 2 from 2 to 1
        |move 1 from 1 to 2""".stripMargin
    Day05.run(input, true) shouldBe "MCD"
  }

  test("Puzzle 2") {
    Day05.run(fromResource("Day05").getLines().mkString("\n"), true) shouldBe "BLSGJSDTS"
  }
}
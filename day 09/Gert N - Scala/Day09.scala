import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.Util.*
import scala.collection.immutable.Nil.head
import scala.collection.mutable
import scala.collection.mutable.HashSet
import scala.io.Source.fromResource

object Day09 {

  type Position = (Int, Int)
  type Rope = Array[Position]
  type Direction = (Int, Int)

  def run(instructions: List[String], ropeLength: Int = 2): Int = {
    val rope = createRope(ropeLength)
    val tailPositions = mutable.HashSet[Position](rope.last)

    instructions.map(parseInstruction).foreach((numSteps, direction) => {
      (1 to numSteps).foreach(_ => {
        moveRope(rope, direction)
        tailPositions.add(rope.last)
      })
    })

    tailPositions.size
  }

  def createRope(ropeLength: Int): Rope = {
    (1 to ropeLength).map(_ => (0, 0)).toArray
  }

  def moveRope(rope: Rope, step: Direction) = {
    rope.update(0, move(rope.head, step))

    for (i <- 1 until rope.length)
      rope.update(i, moveTowards(rope(i), rope(i - 1)))
  }

  def move(position: Position, step: Direction): Position = {
    (position._1 + step._1, position._2 + step._2)
  }

  def moveTowards(a: Position, b: Position): Position = {
    val xDistance = a._1 - b._1
    val yDistance = a._2 - b._2

    if (Math.abs(xDistance) <= 1 && Math.abs(yDistance) <= 1)
      return a

    (if (Math.abs(xDistance) > 1) a._1 - xDistance / 2 else b._1,
      if (Math.abs(yDistance) > 1) a._2 - yDistance / 2 else b._2)
  }

  def parseInstruction(instruction: String): (Int, Direction) = {
    val split = instruction.split(" ")
    (split.last.toInt, parseDirection(split.head))
  }

  def parseDirection(direction: String): Direction = {
    direction match
      case "R" => (1, 0)
      case "L" => (-1, 0)
      case "U" => (0, -1)
      case "D" => (0, 1)
  }
}

class Day09Tests extends AnyFunSuite with Matchers {
  val example: List[String] =
    """R 4
      |U 4
      |L 3
      |D 1
      |R 4
      |D 1
      |L 5
      |R 2""".stripMargin.split("\n").toList

  val example2: List[String] =
    """R 5
      |U 8
      |L 8
      |D 3
      |R 17
      |D 10
      |L 25
      |U 20""".stripMargin.split("\n").toList

  test("Example 1") {
    Day09.run(example) shouldBe 13
  }

  test("Puzzle 1") {
    Day09.run(fromResource("Day09").getLines().toList) shouldBe 5902
  }

  test("Example 2.1") {
    Day09.run(example, 10) shouldBe 1
  }

  test("Example 2.2") {
    Day09.run(example2, 10) shouldBe 36
  }

  test("Puzzle 2") {
    Day09.run(fromResource("Day09").getLines().toList, 10) shouldBe 2445
  }
}
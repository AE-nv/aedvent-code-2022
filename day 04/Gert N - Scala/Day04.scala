import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.Util.*
import scala.io.Source.fromResource

object Day04 {

  def run(pairs: List[String]): Int = {
    pairs.count(hasContainment)
  }

  def run2(pairs: List[String]): Int = {
    pairs.count(isOverlapping)
  }

  def hasContainment(pair: String): Boolean = {
    val sectors = pair.split(",")
    contains(sectors.head, sectors.last) || contains(sectors.last, sectors.head)
  }

  def contains(a: String, b: String): Boolean = {
    val aRange = a.split("-")
    val bRange = b.split("-")

    aRange.head.toInt <= bRange.head.toInt
      && aRange.last.toInt >= bRange.last.toInt
  }

  def isOverlapping(pair: String): Boolean = {
    val sectors = pair.split(",")
    overlaps(sectors.head, sectors.last)
  }

  def overlaps(a: String, b: String): Boolean = {
    val aRange = a.split("-")
    val bRange = b.split("-")

    within(aRange.head.toInt, bRange)
      || within(bRange.head.toInt, aRange)
  }

  def within(rangeBeginning: Int, range: Array[String]): Boolean = {
    rangeBeginning >= range.head.toInt
      && rangeBeginning <= range.last.toInt
  }
}

class Day04Tests extends AnyFunSuite with Matchers {
  test("Example 1") {
    val input =
      """2-4,6-8
        |2-3,4-5
        |5-7,7-9
        |2-8,3-7
        |6-6,4-6
        |2-6,4-8""".stripMargin.split("\n").toList
    Day04.run(input) shouldBe 2
  }

  test("Puzzle 1") {
    Day04.run(fromResource("Day04").getLines().toList) shouldBe 450
  }

  test("Example 2") {
    val input =
      """2-4,6-8
        |2-3,4-5
        |5-7,7-9
        |2-8,3-7
        |6-6,4-6
        |2-6,4-8""".stripMargin.split("\n").toList
    Day04.run2(input) shouldBe 4
  }

  test("Puzzle 2") {
    Day04.run2(fromResource("Day04").getLines().toList) shouldBe 837
  }
}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.Util.*
import scala.io.Source.fromResource

object Day01 {
  def run(input: String, top: Int = 1): Int = {
    input
      .split("\n\n")
      .map(sumSnacksForElf)
      .sorted.reverse
      .take(top)
      .sum
  }

  def sumSnacksForElf(input: String): Int = {
    input.split("\n").map(_.toInt).sum
  }
}

class Day01Tests extends AnyFunSuite with Matchers {
  test("Example 1") {
    val input =
      """1000
        |2000
        |3000
        |
        |4000
        |
        |5000
        |6000
        |
        |7000
        |8000
        |9000
        |
        |10000""".stripMargin
    Day01.run(input) shouldBe 24000
  }

  test("Puzzle 1") {
    val input = fromResource("Day01").getLines().mkString("\n")
    Day01.run(input) shouldBe 67633
  }

  test("Example 2") {
    val input =
      """1000
        |2000
        |3000
        |
        |4000
        |
        |5000
        |6000
        |
        |7000
        |8000
        |9000
        |
        |10000""".stripMargin
    Day01.run(input, 3) shouldBe 45000
  }

  test("Puzzle 2") {
    val input = fromResource("Day01").getLines().mkString("\n")
    Day01.run(input, 3) shouldBe 199628
  }
}
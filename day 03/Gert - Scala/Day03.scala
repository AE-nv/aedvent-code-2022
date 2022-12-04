import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.Util.*
import scala.io.Source.fromResource

object Day03 {

  def run(backpacks: List[String]): Int = backpacks.map(calculateScore).sum

  def run2(backpacks: List[String]): Int = backpacks.grouped(3).map(calculateGroupScore).sum

  def calculateScore(backpack: String): Int = {
    val (front, back) = backpack.splitAt(backpack.length / 2)
    calculateItemScore(front.intersect(back).head)
  }

  def calculateGroupScore(backpacks: List[String]): Int = {
    val badge = backpacks.reduceLeft((set, backpack) => set.intersect(backpack)).head
    calculateItemScore(badge)
  }

  def calculateItemScore(item: Char): Int = {
    if (item > 'a')
      item.toInt - 'a' + 1
    else
      item.toInt - 'A' + 27
  }
}

class Day03Tests extends AnyFunSuite with Matchers {
  test("Example 1") {
    val input =
      """vJrwpWtwJgWrhcsFMMfFFhFp
        |jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        |PmmdzqPrVvPwwTWBwg
        |wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        |ttgJtRGJQctTZtZT
        |CrZsJsPPZsGzwwsLwLmpwMDw""".stripMargin.split("\n").toList
    Day03.run(input) shouldBe 157
  }

  test("Puzzle 1") {
    Day03.run(fromResource("Day03").getLines().toList) shouldBe 7863
  }

  test("Example 2") {
    val input =
      """vJrwpWtwJgWrhcsFMMfFFhFp
        |jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        |PmmdzqPrVvPwwTWBwg
        |wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        |ttgJtRGJQctTZtZT
        |CrZsJsPPZsGzwwsLwLmpwMDw""".stripMargin.split("\n").toList
    Day03.run2(input) shouldBe 70
  }

  test("Puzzle 2") {
    Day03.run2(fromResource("Day03").getLines().toList) shouldBe 2488
  }
}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.Util.*
import scala.collection.mutable
import scala.io.Source.fromResource
import scala.collection.mutable.Stack

object Day06 {

  def run(input: String, windowSize: Int = 4): Int = {
    val firstUniqueWindow = input
      .zipWithIndex
      .sliding(windowSize).find(window => allDifferent(window, windowSize))

    firstUniqueWindow.get.last._2 + 1
  }

  def allDifferent(window: IndexedSeq[(Char, Int)], windowSize: Int): Boolean = {
    window.toList.map(_._1).toSet.size == windowSize
  }
}

class prDay05Tests extends AnyFunSuite with Matchers {
  test("Example 1.1") { Day06.run("bvwbjplbgvbhsrlpgdmjqwftvncz") shouldBe 5 }
  test("Example 1.2") { Day06.run("nppdvjthqldpwncqszvftbrmjlhg") shouldBe 6 }
  test("Example 1.3") { Day06.run("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") shouldBe 10 }
  test("Example 1.4") { Day06.run("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") shouldBe 11 }
  test("Example 1.5") { Day06.run("mjqjpqmgbljsphdztnvjfqwrcgsmlb") shouldBe 7 }

  test("Puzzle 1") { Day06.run(fromResource("Day06").getLines().toList.head) shouldBe 1702 }

  test("Example 2.1") { Day06.run("bvwbjplbgvbhsrlpgdmjqwftvncz", 14) shouldBe 23 }
  test("Example 2.2") { Day06.run("nppdvjthqldpwncqszvftbrmjlhg", 14) shouldBe 23 }
  test("Example 2.3") { Day06.run("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 14) shouldBe 29 }
  test("Example 2.4") { Day06.run("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 14) shouldBe 26 }
  test("Example 2.5") { Day06.run("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 14) shouldBe 19 }

  test("Puzzle 2") { Day06.run(fromResource("Day06").getLines().toList.head, 14) shouldBe 3559 }
}
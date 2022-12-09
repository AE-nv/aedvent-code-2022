import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.Util.*
import scala.collection.immutable.Nil.head
import scala.collection.mutable
import scala.collection.mutable.HashSet
import scala.io.Source.fromResource

object Day10 {

  
  def run(input: List[String]): Int = {
    0
  }
}

class Day10Tests extends AnyFunSuite with Matchers {
  val example: List[String] =
    """R 4
      |U 4
      |L 3
      |D 1
      |R 4
      |D 1
      |L 5
      |R 2""".stripMargin.split("\n").toList

  test("Example 1") {
    Day10.run(example) shouldBe -1
  }

  test("Puzzle 1") {
    Day10.run(fromResource("Day10").getLines().toList) shouldBe -1
  }

//  test("Example 2") {
//    Day10.run(example) shouldBe -1
//  }
//  
//  test("Puzzle 2") {
//    Day10.run(fromResource("Day10").getLines().toList) shouldBe -1
//  }
}
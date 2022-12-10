import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.Util.*
import scala.collection.immutable.Nil.head
import scala.collection.mutable
import scala.collection.mutable.HashSet
import scala.io.Source.fromResource

class Day10 {

  var cycleCount = 0
  var spritePosition = 1
  var cursor = 0
  var signalSum = 0

  def run(input: List[String]): Int = {
    input.foreach(instruction => {
      cycle()
      instruction.take(4) match {
        case "noop" =>
        case "addx" =>
          cycle()
          incrSpritePosition(instruction.drop(5).toInt)
      }
    })

    signalSum
  }

  def cycle(): Unit = {
    cycleCount += 1
    checkSignal()

    draw()
    cursor = (cursor + 1) % 40
  }

  def checkSignal(): Unit = {
    if((cycleCount - 20) % 40 == 0) {
      signalSum += spritePosition * cycleCount
    }
  }

  def draw(): Unit = {
    if (cursor == 0)
      println()

    if(Math.abs(cursor - spritePosition) <= 1)
      print('#')
    else
      print('.')
  }

  def incrSpritePosition(value: Int): Unit = {
    spritePosition += value
  }
}

class Day10Tests extends AnyFunSuite with Matchers {
  test("Example 1") {
    new Day10().run(fromResource("Day10example").getLines().toList) shouldBe 13140
  }

  test("Puzzle 1") {
    new Day10().run(fromResource("Day10").getLines().toList) shouldBe 11820 // EPJBRKAH
  }
}
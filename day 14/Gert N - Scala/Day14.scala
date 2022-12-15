import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.Util.*
import scala.collection.immutable.Map
import scala.collection.immutable.Nil.head
import scala.collection.mutable
import scala.collection.mutable.HashSet
import scala.io.Source.fromResource
import scala.util.control.Breaks.{break, breakable}

class Day14 {

  type Pos = (Int, Int)
  type Grid = mutable.Map[Pos, Char]

  var grid: Grid = _

  def run(input: List[String]): Int = {
    grid = parse(input)
    trickleSand()
  }

  def run2(input: List[String]): Int = {
    grid = parse(input)
    trickleSand(true) + 1
  }

  def trickleSand(withBottom: Boolean = false): Int = {
    var units = 0
    val lowestWall = grid.map(_._1._2).max

    while(trickleUnit((500, 0), lowestWall, withBottom))
      units += 1

    units
  }

  def trickleUnit(initialPos: Pos, lowestWall:Int, withBottom: Boolean): Boolean = {
    var currentPos: Pos = null
    var newPos = Option(initialPos)

    breakable {
      while (newPos.isDefined) {
        currentPos = newPos.get
        if (currentPos._2 > lowestWall)
          break
        newPos = fallDown(currentPos)
      }
    }

    grid.addOne(currentPos, 'o')
    currentPos != initialPos
      && (currentPos._2 <= lowestWall || withBottom)
  }

  def fallDown(pos: Pos): Option[Pos] = {
    if (!grid.contains(pos + (0, 1))) Option(pos + (0, 1))
    else if (!grid.contains(pos + (-1, 1))) Option(pos + (-1, 1))
    else if (!grid.contains(pos + (1, 1))) Option(pos + (1, 1))
    else None
  }

  def parse(input: List[String]): Grid = {
    val grid = mutable.HashSet[(Pos, Char)]()

    input.foreach(path => {
      val coordinates = path.split("->").map(_.trim)

      coordinates.sliding(2).foreach(coordinates => {
        // Make path between coordinates
        val from = toPos(coordinates.head)
        val to = toPos(coordinates.last)
        val trajectory = from - to

        if (trajectory._1 == 0) {
          // walk vertically
          (0 to trajectory._2 by trajectory._2 / Math.abs(trajectory._2))
            .map(i => from + (0, i))
            .foreach(pos => grid.add(pos, '#'))
        } else if(trajectory._2 == 0) {
          // walk horizontally
          (0 to trajectory._1 by trajectory._1 / Math.abs(trajectory._1))
            .map(i => from + (i, 0))
            .foreach(pos => grid.add(pos, '#'))
        }
      })
    })

    mutable.Map() ++ grid.toMap
  }

  def toPos(s: String): Pos = {
    val split = s.split(",")
    (split.head.toInt, split.last.toInt)
  }
}

class Day14Tests extends AnyFunSuite with Matchers {
  test("Example 1") {
    new Day14().run(fromResource("Day14example").getLines().toList) shouldBe 24
  }

  test("Puzzle 1") {
    new Day14().run(fromResource("Day14").getLines().toList) shouldBe 1133
  }

  test("Example 2") {
    new Day14().run2(fromResource("Day14example").getLines().toList) shouldBe 93
  }

  test("Puzzle 2") {
    new Day14().run2(fromResource("Day14").getLines().toList) shouldBe 27566
  }
}
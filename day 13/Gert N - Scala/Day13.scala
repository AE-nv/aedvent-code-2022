import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.Util.*
import scala.collection.immutable.Map
import scala.collection.immutable.Nil.head
import scala.collection.mutable
import scala.collection.mutable.HashSet
import scala.io.Source.fromResource

class Day13 {

  def run(input: List[String]): Int = {
    input.mkString("\n").split("\n\n")
      .zipWithIndex
      .map(pair => (pair._2 + 1, comparePair(pair._1)))
      .filter(_._2)
      .map(_._1)
      .sum
  }

  val controls: Seq[String] = List("[[2]]", "[[6]]")

  def run2(input: List[String]): Int = {
    input
      .filterNot(_.isEmpty)
      .appendedAll(controls)
      .sortWith(compare)
      .zipWithIndex
      .filter(i => controls.contains(i._1))
      .map(i => i._2 + 1)
      .product
  }

  def comparePair(pair: String): Boolean = {
    val split = pair.split("\n")
    compare(split.head, split.last)
  }

  def compare(a: String, b: String): Boolean = {
    areInRightOrder(a, b).get
  }

  def areInRightOrder(a: String, b: String): Option[Boolean] = {
    if (a.startsWith("[") && b.startsWith("["))
      areListsInRightOrder(toList(a), toList(b))
    else if (a.startsWith("["))
      areListsInRightOrder(toList(a), List(b))
    else if (b.startsWith("["))
      areListsInRightOrder(List(a), toList(b))
    else
      areIntegersInRightOrder(a, b)
  }

  def areListsInRightOrder(a: List[String], b: List[String]): Option[Boolean] = {
    a.indices.foreach(i => {
      if(b.length <= i)
        return Some(false)

      val rightOrder = areInRightOrder(a(i), b(i))
      if (rightOrder.isDefined)
        return rightOrder
    })

    if (a.length < b.length)
      Some(true)
    else
      None
  }

  def areIntegersInRightOrder(a: String, b: String): Option[Boolean] = {
    if (a.toInt == b.toInt)
      None
    else
      Some(a.toInt <= b.toInt)
  }

  def toList(s: String): List[String] = {
    val raw = s.stripPrefix("[").stripSuffix("]")

    if(raw.isEmpty)
      return List()

    val items = mutable.Queue[String]()
    var previousCut = 0
    var level = 0

    raw.indices.foreach(i => {
      val char = raw.charAt(i)

      if (char == '[')
        level += 1
      else if (char == ']')
        level -= 1
      else if (char == ',' && level == 0) {
        items.addOne(raw.substring(previousCut, i))
        previousCut = i + 1
      }
    })

    items.addOne(raw.substring(previousCut))
    items.toList
  }
}

class Day13Tests extends AnyFunSuite with Matchers {
  test("Example 1") {
    new Day13().run(fromResource("Day13example").getLines().toList) shouldBe 13
  }

  test("Puzzle 1") {
    new Day13().run(fromResource("Day13").getLines().toList) shouldBe 5292
  }

  test("Example 2") {
    new Day13().run2(fromResource("Day13example").getLines().toList) shouldBe 140
  }

  test("Puzzle 2") {
    new Day13().run2(fromResource("Day13").getLines().toList) shouldBe 23868
  }
}
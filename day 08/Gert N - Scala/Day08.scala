import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.Util.*
import scala.collection.mutable
import scala.collection.mutable.HashSet
import scala.io.Source.fromResource

object Day08 {

  def run(input: List[String]): Int = {
    countVisible(input)
  }

  def run2(input: List[String]): Int = {
    findMostTreesVisible(input)
  }

  def countVisible(input: List[String]): Int = {
    val trees = input.map(_.toCharArray).map(trees => trees.map(tree => Integer.parseInt(tree.toString)))
    var visible = 0

    for(x <- trees.indices) {
      for(y <- trees(x).indices) {
        if(isVisible(trees, x, y))
          visible += 1
      }
    }

    visible
  }

  def findMostTreesVisible(input: List[String]): Int = {
    val trees = input.map(_.toCharArray).map(trees => trees.map(tree => Integer.parseInt(tree.toString)))
    var bestNumVisible = 0

    for (x <- trees.indices) {
      for (y <- trees(x).indices) {
        val numTreesVisible = countNumTreesVisible(trees, x, y)
        if(numTreesVisible > bestNumVisible)
          bestNumVisible = numTreesVisible
      }
    }

    bestNumVisible
  }

  def isVisible(trees: List[Array[Int]], x: Int, y: Int): Boolean = {
    isVisibleFromLeft(trees, x, y)
      || isVisibleFromRight(trees, x, y)
      || isVisibleFromTop(trees, x, y)
      || isVisibleFromBottom(trees, x, y)
  }

  def isVisibleFromLeft(trees: List[Array[Int]], x: Int, y: Int): Boolean = {
    for(i <- 0 until x)
      if(trees(i)(y) >= trees(x)(y))
        return false
    true
  }

  def isVisibleFromRight(trees: List[Array[Int]], x: Int, y: Int): Boolean = {
    for (i <- x + 1 until trees.length)
      if (trees(i)(y) >= trees(x)(y))
        return false
    true
  }

  def isVisibleFromTop(trees: List[Array[Int]], x: Int, y: Int): Boolean = {
    for (i <- 0 until y)
      if (trees(x)(i) >= trees(x)(y))
        return false
    true
  }

  def isVisibleFromBottom(trees: List[Array[Int]], x: Int, y: Int): Boolean = {
    for (i <- y + 1 until trees(x).length)
      if (trees(x)(i) >= trees(x)(y))
        return false
    true
  }

  def countNumTreesVisible(trees: List[Array[Int]], x: Int, y: Int): Int = {
    countVisibleToLeft(trees, x, y)
      * countVisibleToRight(trees, x, y)
      * countVisibleToTop(trees, x, y)
      * countVisibleToBottom(trees, x, y)
  }

  def countVisibleToLeft(trees: List[Array[Int]], x: Int, y: Int): Int = {
    var visible = 0
    for (i <- x - 1 to 0 by -1)
      if (trees(i)(y) >= trees(x)(y)) {
        visible += 1
        return visible
      } else {
        visible += 1
      }

    visible
  }

  def countVisibleToRight(trees: List[Array[Int]], x: Int, y: Int): Int = {
    var visible = 0
    for (i <- x + 1 until trees.length)
      if (trees(i)(y) >= trees(x)(y)) {
        visible += 1
        return visible
      } else {
        visible += 1
      }

    visible
  }

  def countVisibleToTop(trees: List[Array[Int]], x: Int, y: Int): Int = {
    var visible = 0
    for (i <- y - 1 to 0 by -1)
      if (trees(x)(i) >= trees(x)(y)) {
        visible += 1
        return visible
      } else {
        visible += 1
      }

    visible
  }

  def countVisibleToBottom(trees: List[Array[Int]], x: Int, y: Int): Int = {
    var visible = 0
    for (i <- y + 1 until trees(x).length)
      if (trees(x)(i) >= trees(x)(y)) {
        visible += 1
        return visible
      } else {
        visible += 1
      }

    visible
  }
}

class Day08Tests extends AnyFunSuite with Matchers {
  val example: List[String] =
    """30373
      |25512
      |65332
      |33549
      |35390""".stripMargin.split("\n").toList

  test("Example 1") {
    Day08.run(example) shouldBe 21
  }

  test("Example 2") {
    Day08.run2(example) shouldBe 8
  }

  test("Puzzle 1") {
    Day08.run(fromResource("Day08").getLines().toList) shouldBe 1672
  }

  test("Puzzle 2") {
    Day08.run2(fromResource("Day08").getLines().toList) shouldBe 327180
  }
}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.Util.*
import scala.collection.immutable.Map
import scala.collection.immutable.Nil.head
import scala.collection.mutable
import scala.collection.mutable.HashSet
import scala.io.Source.fromResource

class Day12 {

  type Pos = (Int, Int)

  class Node(val pos: Pos, val neighbours: List[Pos], var value: Char, var distanceFromStart: Int) {

    if (isStart) { distanceFromStart = 0 }

    def isStart: Boolean = value == 'S'
    def isEnd: Boolean = value == 'E'

    def nodeValue: Int = {
      if (isStart) 'a'.toInt
      else if (isEnd) 'z'.toInt
      else value.toInt
    }
  }

  def run(input: List[String], startNodes: List[Char] = List('S')): Long = {
    val grid = parseGrid(input)
    shortestPathFrom(grid, grid.filter(node => startNodes.contains(node.value)))
  }

  def shortestPathFrom(grid: List[Node], startNodes: List[Node]): Int = {
    startNodes.foreach(_.distanceFromStart = 0)
    val nodesToConsider = mutable.HashSet[Node](startNodes: _*)
    val consideredNodes = mutable.HashSet[Node]()

    while(nodesToConsider.nonEmpty) {
      val currentNode = nodesToConsider.head
      nodesToConsider.remove(currentNode)
      consideredNodes.add(currentNode)

      // update distance of reachable neighbours if shorter
      grid
        .filter(node => currentNode.neighbours.contains(node.pos))
        .filter(neighbour => currentNode.nodeValue + 1 >= neighbour.nodeValue)
        .filter(neighbour => currentNode.distanceFromStart + 1 < neighbour.distanceFromStart)
        .foreach(neighbour => {
          neighbour.distanceFromStart = currentNode.distanceFromStart + 1
          nodesToConsider.add(neighbour)
      })
    }

    grid.find(_.isEnd).get.distanceFromStart
  }

  def parseGrid(input: List[String]): List[Node] = {
    val array = input.map(_.toArray)

    array.indices.flatMap(x => {
      array(x).indices.map(y => {
        val node = (x, y)
        val neighbours = getNeighbours(node)
          .filterNot(_._1 >= array.length)
          .filterNot(_._2 >= array(x).length)
          .filterNot(_._1 < 0)
          .filterNot(_._2 < 0)

        new Node((x, y), neighbours, array(x)(y), Integer.MAX_VALUE)
      }).toList
    }).toList
  }

  def getNeighbours(pos: Pos): List[Pos] = {
    List(
      pos + (-1, 0),
      pos + (1, 0),
      pos + (0, -1),
      pos + (0, 1))
  }
}

class Day12Tests extends AnyFunSuite with Matchers {
  test("Example 1") {
    new Day12().run(fromResource("Day12example").getLines().toList) shouldBe 31
  }

  test("Puzzle 1") {
    new Day12().run(fromResource("Day12").getLines().toList) shouldBe 383
  }

  test("Example 2") {
    new Day12().run(fromResource("Day12example").getLines().toList, List('S', 'a')) shouldBe 29
  }

  test("Puzzle 2") {
    new Day12().run(fromResource("Day12").getLines().toList, List('S', 'a')) shouldBe 377
  }
}
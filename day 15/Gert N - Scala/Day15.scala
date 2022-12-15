import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.Util.*
import scala.collection.immutable.{Map, NumericRange}
import scala.collection.immutable.Nil.head
import scala.collection.mutable
import scala.collection.mutable.HashSet
import scala.io.Source.fromResource
import scala.util.control.Breaks.{break, breakable}

class Day15 {

  type Pos = (Long, Long)

  var sensors: mutable.Set[(Pos, Long)] = mutable.HashSet[(Pos, Long)]()
  var beacons: mutable.Set[Pos] = mutable.HashSet[Pos]()
  var minPos: Int = Int.MinValue
  var maxPos: Int = Int.MaxValue

  def run(input: List[String], row: Long): Int = {
    input.foreach(parse)
    countWithinRange(row)
  }

  def run2(input: List[String], bound: Int = 20): Long = {
    minPos = 0
    maxPos = bound
    input.foreach(parse)
    val distressLocation = findDistressLocation()
    distressLocation._1 * 4000000.toLong + distressLocation._2
  }

  def findDistressLocation(): Pos = {
    sensors.foreach(sensor => {
      (sensor._1._1 - sensor._2 - 1 to sensor._1._1 + sensor._2 + 1).foreach(x => {
        val distanceRemaining = sensor._2 - math.abs(x - sensor._1._1) + 1
        val locations = List((x, sensor._1._2 - distanceRemaining), (x, sensor._1._2 + distanceRemaining))

        val distressLocation = locations
          .filterNot(inRangeOfSensor)
          .find(inBounds)

        if(distressLocation.isDefined)
          return distressLocation.get
      })
    })

    (-1, -1)
  }

  def inBounds(pos: Pos): Boolean = {
    pos._1 >= 0 && pos._1 <= maxPos && pos._2 >= 0 && pos._2 <= maxPos
  }

  def inRangeOfSensor(pos: Pos): Boolean = {
    sensors.exists(sensor => inRange(pos, sensor))
  }

  def inRange(pos: Pos, sensor: (Pos, Long)): Boolean = {
    calculateDistance(pos, sensor._1) <= sensor._2
  }

  def countWithinRange(fixedY: Long): Int = {
    val ranges = sensors.map(sensor => {
      val distanceRemaining = sensor._2 - math.abs(fixedY - sensor._1._2)
      sensor._1._1 - distanceRemaining to sensor._1._1 + distanceRemaining
    })

    val itemsInRange = simplify(ranges).map(_.size).sum
    val beaconsOnRow = beacons.count(_._2 == fixedY)
    itemsInRange - beaconsOnRow
  }

  def simplify(ranges: mutable.Set[NumericRange.Inclusive[Long]]): mutable.Set[NumericRange.Inclusive[Long]] = {
    val simplifiedRanges = mutable.Set[NumericRange.Inclusive[Long]]()

    while(ranges.nonEmpty) {
      val range = ranges.head
      ranges.remove(range)

      val overlapping = ranges.filter(other => other.contains(range.start) || other.contains(range.end))
      overlapping.foreach(ranges.remove)

      if(overlapping.isEmpty)
        simplifiedRanges.add(range)
      else
        ranges.add(math.min(range.start, overlapping.map(_.start).min)
          to math.max(range.end, overlapping.map(_.end).max))
    }

    simplifiedRanges.filterNot(_.isEmpty)
  }

  def parse(input: String): Unit = {
    val matches = "(=-?\\d+)".r
      .findAllMatchIn(input)
      .map(m => Integer.parseInt(m.matched.stripPrefix("=")).toLong)
      .toList
    val sensor = (matches.head, matches(1))
    val beacon = (matches(2), matches(3))
    sensors.add(sensor, calculateDistance(sensor, beacon))
    beacons.add(beacon)
  }

  def calculateDistance(a: Pos, b: Pos): Long = {
    Math.abs(a._1 - b._1) + Math.abs(a._2 - b._2)
  }
}

class Day15Tests extends AnyFunSuite with Matchers {
  test("Example 1") {
    new Day15().run(fromResource("Day15example").getLines().toList, 10) shouldBe 26
  }

  test("Puzzle 1") {
    new Day15().run(fromResource("Day15").getLines().toList, 2000000) shouldBe 4883971
  }

  test("Example 2") {
    new Day15().run2(fromResource("Day15example").getLines().toList) shouldBe 56000011
  }

  test("Puzzle 2") {
    new Day15().run2(fromResource("Day15").getLines().toList, 4000000) shouldBe 12691026767556L
  }
}
import scala.io.Source
import scala.util.{Success, Using}

object DayFour {

  private def overlapCheckPartOne: Array[Array[Int]] => Boolean = { r =>
      val Array(Array(a, b), Array(c, d)) = r
      (a >= c && b <= d) || (a <= c && b >= d)
  }

  private def overlapCheckPartTwo: Array[Array[Int]] => Boolean = { r =>
    val Array(Array(a, b), Array(c, d)) = r
    ((c <= a) && (a <= d)) || ((c <= b) && (b <= d)) || ((a <= c) && (c <= b) ) || ((a <= d) && (d <= b))
  }

  def amountOverlapping(ranges: Vector[String], overlapCheck: Array[Array[Int]] => Boolean): Int = {
    ranges.map{
      _.split(",")
      .map(_.split('-'))
      .map(_.map(_.toInt))
    }
      .foldLeft(0){(x, y) =>
        if(overlapCheck(y)) x + 1
        else x
      }
  }

  def main(args: Array[String]): Unit = {

    val input = Using(Source.fromFile("src/main/resources/day4/input.txt")) { source =>
      source.getLines.toVector
    }

    input match {
      case Success(i) => println(amountOverlapping(i, overlapCheckPartOne))
      case _ => throw new Exception("File could not be read")
    }

    input match {
      case Success(i) => println(amountOverlapping(i, overlapCheckPartTwo))
      case _ => throw new Exception("File could not be read")
    }
  }
}

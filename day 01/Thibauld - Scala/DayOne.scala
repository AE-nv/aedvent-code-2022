import scala.io.Source
import scala.util.{Success, Using}

object DayOne {

  private def getMostCals(cals: String): Array[Int] = {
    cals.split("\n\n")
      .map{
        _.split("\n")
          .map(_.toInt)
          .sum
      }
  }

  def getSingleMostCals(cals: String): Int = {
    this.getMostCals(cals)
      .max
  }

  def getThreeMostCals(cals: String): Int = {
    this.getMostCals(cals)
      .sorted(Ordering.Int.reverse)
      .take(3)
      .sum
  }

  def main(args: Array[String]): Unit = {

    val input = Using(Source.fromFile("src/main/resources/day1/input.txt")) { source =>
      source.mkString
    }

    input match {
      case Success(i) => println(getSingleMostCals(i))
      case _ => throw new Exception("File could not be read")
    }

    input match {
      case Success(i) => println(getThreeMostCals(i))
      case _ => throw new Exception("File could not be read")
    }
  }

}

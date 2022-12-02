import scala.io.Source
import scala.util.{Success, Using}

object DayTwo {

  private val scores = Map("A" -> 1, "B" -> 2, "C" -> 3)
  private val wins = Map("A" -> "B", "B" -> "C", "C" -> "A")
  private val losses = Map("A" -> "C", "B" -> "A", "C" -> "B")

  private def compare(tup: (String, String)): Int = {
    if(tup._1 == tup._2){
      3 + scores(tup._2)
    }
    else if(wins(tup._1) == tup._2){
      6 + scores(tup._2)
    }
    else {
      0 + scores(tup._2)
    }
  }

  val firstReplace: Array[String] => (String, String) = {
    case Array(x, "X") => (x, "A")
    case Array(x, "Y") => (x, "B")
    case Array(x, "Z") => (x, "C")
  }

  val secondReplace: Array[String] => (String, String) = {
    case Array (x, "X") => (x, losses(x))
    case Array (x, "Y") => (x, x)
    case Array (x, "Z") => (x, wins(x))
  }

  def countPoints(matchups: Vector[String], rplc: Array[String] => (String, String)): Int = {

    matchups
      .map(_.split(" "))
      .map(rplc)
      .map(compare)
      .sum
  }

  def main(args: Array[String]): Unit = {

    val input = Using(Source.fromFile("src/main/resources/day2/input.txt")) { source =>
      source.getLines.toVector
    }

    input match {
      case Success(i) => println(countPoints(i, firstReplace))
      case _ => throw new Exception("File could not be read")
    }

    input match {
      case Success(i) => println(countPoints(i, secondReplace))
      case _ => throw new Exception("File could not be read")
    }
  }
}

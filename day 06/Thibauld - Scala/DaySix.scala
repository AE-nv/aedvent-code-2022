import scala.io.Source
import scala.util.{Success, Using}

object DaySix {

  def firstUniqueIndex(s: String, amountOfUniqueChars: Int): Int = {
    s.zipWithIndex
      .sliding(amountOfUniqueChars)
      .toVector
      .takeWhile{ v =>
        v.map(_._1).mkString.distinct.length < amountOfUniqueChars
      }
      .last.last._2 + 2
  }

  def main(args: Array[String]): Unit = {

    val input = Using(Source.fromFile("src/main/resources/day6/input.txt")) { source =>
      source.mkString
    }

    input match {
      case Success(i) => println(firstUniqueIndex(i, 4))
      case _ => throw new Exception("File could not be read")
    }

    input match {
      case Success(i) => println(firstUniqueIndex(i, 14))
      case _ => throw new Exception("File could not be read")
    }
  }
}

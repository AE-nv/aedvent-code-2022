import scala.io.Source
import scala.util.{Success, Using}

object DayThree {

  private def getDuplicates(vec: Vector[String]): Char = {
    vec.foldLeft(vec.head) { (a, b) =>
      a.filter(c => b.contains(c))
    }.head
  }

  def countPriorities(items: Vector[Vector[String]]): Int = {
    items
      .map(getDuplicates)
      .map{ c =>
        if(c.isUpper) c.toInt - 38
        else c.toInt - 96
      }
      .sum
  }

  def main(args: Array[String]): Unit = {

    val input = Using(Source.fromFile("src/main/resources/day3/input.txt")) { source =>
      source.getLines.toVector
    }

    input match {
      case Success(i) => println(countPriorities(i.map { r =>
        val itemSize = r.length
        Vector(r.substring(0, itemSize / 2), r.substring(itemSize / 2, itemSize))
      }))
      case _ => throw new Exception("File could not be read")
    }

    input match {
      case Success(i) => println(countPriorities(i.grouped(3).toVector))
      case _ => throw new Exception("File could not be read")
    }
  }
}

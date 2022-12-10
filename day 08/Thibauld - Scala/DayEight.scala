import scala.io.Source
import scala.util.{Success, Using}

object DayEight {

  def getVisibleTrees(input: Vector[String]): Int = {
    val forest = input.map(_.split("").map(_.toInt).toVector)
    val height = forest.size
    val width = forest.head.length
    val indices =
      for(
        x <- 1 until height - 1;
        y <- 1 until width - 1
      ) yield (x,y)

    indices.map { i =>
      val cols = forest.map(_ (i._2))
      val up = cols.slice(0, i._1)
      val down = cols.slice(i._1 + 1, height)
      val left = forest(i._1).slice(0, i._2)
      val right = forest(i._1).slice(i._2 + 1, width)
      Seq(up, down, left, right).find(_.forall(_ < forest(i._1)(i._2))) match {
        case Some(_) => 1
        case None => 0
      }
    }.sum + ((2 * (height - 2)) + (width * 2))
  }

  def getBestPosition(input: Vector[String]): Int = {
    val forest = input.map(_.split("").map(_.toInt).toVector)
    val height = forest.size
    val width = forest.head.length
    val indices =
      for(
        x <- 1 until height - 1;
        y <- 1 until width - 1
      ) yield (x,y)

    indices.map { i =>
      val cols = forest.map(_ (i._2))
      val up = cols.slice(0, i._1).reverse
      val down = cols.slice(i._1 + 1, height)
      val left = forest(i._1).slice(0, i._2).reverse
      val right = forest(i._1).slice(i._2 + 1, width)
      Seq(up, down, left, right).map { row =>
        val numOfTreesVisible = row.takeWhile(_ < forest(i._1)(i._2)).size
        if(numOfTreesVisible == row.size) numOfTreesVisible
        else numOfTreesVisible + 1
      }.product
    }.max
  }

  def main(args: Array[String]): Unit = {
    val input = Using(Source.fromFile("src/main/resources/day8/input.txt")) { source =>
      source.getLines.toVector
    }

    input match {
      case Success(i) => println(getVisibleTrees(i))
      case _ => throw new Exception("File could not be read")
    }

    input match {
      case Success(i) => println(getBestPosition(i))
      case _ => throw new Exception("File could not be read")
    }
  }
}

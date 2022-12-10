import scala.io.Source
import scala.util.{Success, Using}

object DaySeven {

  private def calculateSizes(tree: Tree, filtered: Seq[Int]): (Int, Seq[Int]) = {
    tree match {
      case Leaf(_, size, _, _) => (size, filtered)
      case Node(_, children, _) =>
        val nodeSizes = children.map(calculateSizes(_, filtered))
        val nodeSize = nodeSizes.map(_._1).sum
        val nextFil = nodeSizes.flatMap(_._2)
        (nodeSize, nextFil :+ nodeSize)
    }
  }

  def f1: (Int, Seq[Int]) => Int = (_, allSizes) => allSizes.filter(_ <= 1000000).sum
  def f2: (Int, Seq[Int]) => Int = { (rootSize, allSizes) =>
    val unusedToFind = 30000000 - (70000000 - rootSize)
    allSizes.filter(_ >= unusedToFind).min
  }

  def getLargeDirs(cmds: Vector[String], f: (Int, Seq[Int]) => Int): Int = {
    val root: Tree = Node("/", Seq.empty[Tree], None)
    var current: Tree = root
    for(line <- cmds.tail){
      if(line.startsWith("$ cd")){
        current =
        line.split(" ").toVector.last match {
          case ".." => current.parent.getOrElse(throw new Exception())
          case n => current.children.find(_.name == n).getOrElse(throw new Exception(s"Child with name $n not found"))
        }
      }
      else if(line.startsWith("$ ls")){}
      else{
        if(line.startsWith("dir")) current.children = current.children :+ Node(line.split(" ").last, Seq.empty, Option(current))
        else current.children = current.children :+ Leaf(line.split(" ").last, line.split(" ").head.toInt, Option(current))
      }
    }

    val sizes = this.calculateSizes(root, Seq.empty[Int])
    f(sizes._1, sizes._2)
  }

  def main(args: Array[String]): Unit = {
    val input = Using(Source.fromFile("src/main/resources/day7/input.txt")) { source =>
      source.getLines.toVector
    }

    input match {
      case Success(i) => println(this.getLargeDirs(i, f1))
      case _ => throw new Exception("File could not be read")
    }

    input match {
      case Success(i) => println(this.getLargeDirs(i, f2))
      case _ => throw new Exception("File could not be read")
    }
  }
}
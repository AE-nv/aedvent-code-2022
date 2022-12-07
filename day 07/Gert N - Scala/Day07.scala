import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.Util.*
import scala.collection.mutable
import scala.collection.mutable.HashSet
import scala.io.Source.fromResource

object Day07 {

  case class Dir(name: String, children: mutable.HashSet[Dir], parent: Dir, var size: Long)

  def run(input: List[String]): Long = {
    process(input).toList.map(dir => dir.size).filter(_ <= 100000).sum
  }

  def run2(input: List[String]): Long = {
    val dirSizes = process(input).toList.map(dir => dir.size)
    dirSizes.filter(_ >= dirSizes.max - 40000000).min
  }


  def process(input: List[String]): mutable.HashSet[Dir] = {
    val root = Dir("/", new mutable.HashSet(), null, 0)
    val allDirs = mutable.HashSet[Dir](root)
    var currentDir = root

    input.foreach(line => {
      if (line.startsWith("$ cd")) {
        line.stripPrefix("$ cd ") match
          case "/" => currentDir = root
          case ".." => currentDir = currentDir.parent
          case folderName => currentDir = currentDir.children.find(_.name == folderName).get
      } else if (line == "$ ls") {
        // Continue to parse ls output
      } else if (line.startsWith("dir ")) {
        val dir = Dir(line.stripPrefix("dir "), new mutable.HashSet(), currentDir, 0)
        currentDir.children.add(dir)
        allDirs.add(dir)
      } else {
        addSizeToDirs(line.split(" ").head.toLong, currentDir)
      }
    })

    allDirs
  }

  def addSizeToDirs(size: Long, dir: Dir): Unit = {
    var dirToAddSizeTo = dir
    while (dirToAddSizeTo != null) {
      dirToAddSizeTo.size += size
      dirToAddSizeTo = dirToAddSizeTo.parent
    }
  }
}

class Day07Tests extends AnyFunSuite with Matchers {
  val example: List[String] =
    """$ cd /
      |$ ls
      |dir a
      |14848514 b.txt
      |8504156 c.dat
      |dir d
      |$ cd a
      |$ ls
      |dir e
      |29116 f
      |2557 g
      |62596 h.lst
      |$ cd e
      |$ ls
      |584 i
      |$ cd ..
      |$ cd ..
      |$ cd d
      |$ ls
      |4060174 j
      |8033020 d.log
      |5626152 d.ext
      |7214296 k""".stripMargin.split("\n").toList

  test("Example 1") {
    Day07.run(example) shouldBe 95437
  }

  test("Example 2") {
    Day07.run2(example) shouldBe 24933642
  }

  test("Puzzle 1") {
    Day07.run(fromResource("Day07").getLines().toList) shouldBe 1297159
  }

  test("Puzzle 2") {
    Day07.run2(fromResource("Day07").getLines().toList) shouldBe 3866390
  }
}
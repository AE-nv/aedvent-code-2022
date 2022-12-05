import scala.collection.mutable
import scala.io.Source
import scala.util.{Success, Using}

object DayFive {

  private def getInitialStack(startSetup: String): Seq[mutable.Stack[Char]] = {
    val start = startSetup.split("\n").toSeq
    val amountOfStacks = start.last.split("  ").length
    val r = 1 until (amountOfStacks * 4) by 4

    val stacks = Seq.fill(amountOfStacks)(mutable.Stack[Char]())

    start.map(r.collect(_).zipWithIndex)
      .reverse
      .tail
      .foreach { line =>
        line.foreach { case (box, i) =>
          if (box != ' ') stacks(i).push(box)
        }
      }
    stacks
  }

  private def transformInstructions(instructionManual: String): Seq[Instruction] = {
    instructionManual.split("\n")
      .map(_.split(" "))
      .map(i => Instruction(i(1).toInt, i(3).toInt - 1, i(5).toInt - 1))
  }

  def getTopCrates(setup: String, crane: String): String = {
    val Array(startSetup, instructionManual) = setup.split("\n\n")

    val instructions = transformInstructions(instructionManual)
    val stacks = getInitialStack(startSetup)

    for(instruction <- instructions) {
      val elmsToPush = (1 to instruction.amount).foldLeft(Seq[Char]()){ (a, b) =>
        val c = stacks(instruction.fromStack).pop
        a :+ c
      }
      stacks(instruction.toStack).pushAll{
        crane match {
          case "CrateMover 9000" => elmsToPush
          case "CrateMover 9001" => elmsToPush.reverse
        }
      }
    }

    stacks.map(_.top).mkString
  }

  def main(args: Array[String]): Unit = {

    val input = Using(Source.fromFile("src/main/resources/day5/input.txt")) { source =>
      source.mkString
    }

    input match {
      case Success(i) => println(getTopCrates(i, "CrateMover 9000"))
      case _ => throw new Exception("File could not be read")
    }

    input match {
      case Success(i) => println(getTopCrates(i, "CrateMover 9001"))
      case _ => throw new Exception("File could not be read")
    }
  }

}

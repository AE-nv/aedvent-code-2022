import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.Util.*
import scala.io.Source.fromResource

object Day02 {

  val rock = 'A'
  val paper = 'B'
  val scissor = 'C'

  def run(input: List[String]): Int = {
    input.map(calculateScore).sum
  }

  def run2(input: List[String]): Int = {
    input.map(calculateScoreByOutcome).sum
  }

  def calculateScore(input: String): Int = {
    val opponentMove = input.charAt(0)
    val playerMove = translateMove(input.charAt(2))

    calculateMoveScore(playerMove) + calculateOutcome(opponentMove, playerMove)
  }

  def translateMove(move: Char): Char = {
    move match
      case 'X' => rock
      case 'Y' => paper
      case 'Z' => scissor
  }

  def calculateScoreByOutcome(input: String): Int = {
    val opponentMove = input.charAt(0)
    val outcome = input.charAt(2)

    val playerMove = determineMove(opponentMove, outcome)

    calculateMoveScore(playerMove) + calculateOutcome(opponentMove, playerMove)
  }

  def determineMove(opponent: Char, outcome: Char): Char = {
    outcome match {
      case 'X' => // Lose
        opponent match
          case this.rock => this.scissor
          case this.paper => this.rock
          case this.scissor => this.paper

      case 'Y' => // Draw
        opponent match
          case this.rock => this.rock
          case this.paper => this.paper
          case this.scissor => this.scissor

      case 'Z' => // Win
        opponent match
          case this.rock => this.paper
          case this.paper => this.scissor
          case this.scissor => this.rock
    }
  }

  def calculateMoveScore(move: Char): Int = {
    move match
      case this.rock => 1
      case this.paper => 2
      case this.scissor => 3
  }

  def calculateOutcome(opponentMove: Char, move: Char): Int = {
    opponentMove match {
      case this.rock =>
        move match
          case this.rock => 3
          case this.paper => 6
          case this.scissor => 0

      case this.paper =>
        move match
          case this.rock => 0
          case this.paper => 3
          case this.scissor => 6

      case this.scissor =>
        move match
          case this.rock => 6
          case this.paper => 0
          case this.scissor => 3
    }
  }
}

class Day02Tests extends AnyFunSuite with Matchers {
  test("Example 1") {
    val input =
      """A Y
        |B X
        |C Z""".stripMargin.split("\n").toList
    Day02.run(input) shouldBe 15
  }

  test("Puzzle 1") {
    Day02.run(fromResource("Day02").getLines().toList) shouldBe 12645
  }

  test("Example 2") {
    val input =
      """A Y
        |B X
        |C Z""".stripMargin.split("\n").toList
    Day02.run2(input) shouldBe 12
  }

  test("Puzzle 2") {
    Day02.run2(fromResource("Day02").getLines().toList) shouldBe 11756
  }
}
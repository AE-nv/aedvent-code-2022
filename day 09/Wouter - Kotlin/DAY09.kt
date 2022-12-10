package days

import AOCAssignmentReader
import DataStructureHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.math.ceil

class DAY09 {
    val day = 9
    val eo1 = 13
    val eo2 = 36
    val ei1: String = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent()

    val ei2: String = """
        R 5
        U 8
        L 8
        D 3
        R 17
        D 10
        L 25
        U 20
    """.trimIndent()

    val dh: DataStructureHelper = DataStructureHelper()

    data class Grid(val head: Head, val tail: Tail) {
        fun performAction(action: String): Grid {
            val direction = action[0]
            val iterations = (1..action.split(" ")[1].toInt()).toList()
            return iterations.fold(this) { grid, _ ->
                Grid(
                    grid.head.pull(direction),
                    grid.tail.follow(grid.head.pull(direction).position, grid.head.position)
                )
            }
        }
    }

    data class Coordinate(val x: Int, val y: Int) {
        fun isNotAdjacent(other: Coordinate): Boolean {
            return abs(other.x - x) > 1 || abs(other.y - y) > 1
        }
    }

    data class Head(val position: Coordinate) {
        fun pull(direction: Char): Head = when (direction) {
            'U' -> Head(Coordinate(position.x, position.y + 1))
            'D' -> Head(Coordinate(position.x, position.y - 1))
            'R' -> Head(Coordinate(position.x + 1, position.y))
            'L' -> Head(Coordinate(position.x - 1, position.y))
            else -> this
        }
    }

    data class Tail(val position: Coordinate, val history: Set<Coordinate>) {
        fun follow(headPosition: Coordinate, oldHead: Coordinate): Tail {
            return if (position.isNotAdjacent(headPosition)) {
                Tail(oldHead, history + oldHead)
            } else {
                this
            }
        }
    }

    data class Snake(val segments: List<Segment>) {
        fun moveHead(direction: Char): Snake {
            val newHead = segments[0].moveInDirection(direction)
            return segments.subList(1, segments.size).fold(Snake(listOf(newHead))) { snake, nextSegment ->
                if (snake.segments.last().position.isNotAdjacent(nextSegment.position)) Snake(snake.segments + nextSegment.moveTowards(snake.segments.last().position)) else Snake(
                    snake.segments + nextSegment
                )
            }
        }
    }

    data class Segment(val position: Coordinate, val history: Set<Coordinate>) {
        fun moveInDirection(direction: Char): Segment = when (direction) {
            'U' -> moveTo(Coordinate(position.x, position.y + 1))
            'D' -> moveTo(Coordinate(position.x, position.y - 1))
            'R' -> moveTo(Coordinate(position.x + 1, position.y))
            'L' -> moveTo(Coordinate(position.x - 1, position.y))
            else -> this
        }
        fun moveTowards(coordinate: Coordinate): Segment = moveTo(Coordinate(towards(coordinate.x, position.x),towards(coordinate.y, position.y)))

        fun towards(to: Int, from: Int) = when {
            to - from > 0 -> from + 1
            to - from < 0 -> from -1
            else -> to
        }
        fun moveTo(coordinate: Coordinate): Segment = Segment(coordinate,history + coordinate)

    }

    fun implementP1(input: String): Int {
        return dh.lines(input).fold(
            Grid(
                Head(Coordinate(0, 0)),
                Tail(Coordinate(0, 0), setOf(Coordinate(0, 0)))
            )
        ) { grid, line -> grid.performAction(line) }.tail.history.size
    }

    fun implementP2(input: String): Int {
        val seg = Segment(Coordinate(50000, 50000), setOf(Coordinate(50000,50000)))
        return dh.lines(input).fold(
            Snake(
                listOf(
                    seg,
                    seg,
                    seg,
                    seg,
                    seg,
                    seg,
                    seg,
                    seg,
                    seg,
                    seg
                )
            )
        ) { snek, line -> (1..line.split(" ")[1].toInt()).fold(snek) { snake, _ -> snake.moveHead(line[0]) } }.segments.last().history.size
    }

    @Test
    fun runp1() {
        assertThat(implementP1(ei1)).isEqualTo(eo1)
        println(implementP1(AOCAssignmentReader().readInputForDay(day)))
    }

    @Test
    fun runp2() {
        assertThat(implementP2(ei2)).isEqualTo(eo2)
        println(implementP2(AOCAssignmentReader().readInputForDay(day)))
    }

}
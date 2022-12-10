package days

import AOCAssignmentReader
import DataStructureHelper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class DAY08 {
    val day = 8
    val eo1 = 21
    val eo2 = 8
    val ei: String = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent()

    val dh: DataStructureHelper = DataStructureHelper()

    data class TreeGrid(val trees: Set<Tree>) {
        fun addTrees(line: String, row: Int) =
            TreeGrid(trees + line.mapIndexed { index, height -> Tree(height.digitToInt(), index, row, false, 1) })

        fun numberOfTreesVisible() = trees
            .map { thisTree -> thisTree.isVisibleHorizontally(TreeLine(trees.filter { thisTree.y == it.y })) }
            .map { thisTree -> thisTree.isVisibleVertically(TreeLine(trees.filter { thisTree.x == it.x })) }
            .count { it.isVisible }

        fun bestScenicScore() = trees
            .map { thisTree -> thisTree.mapHorizontalScenicScore(TreeLine(trees.filter { thisTree.y == it.y })) }
            .map { thisTree -> thisTree.mapVerticalScenicScore(TreeLine(trees.filter { thisTree.x == it.x })) }
            .maxByOrNull { it.scenicScore }!!.scenicScore


    }

    data class TreeLine(val trees: List<Tree>)
    data class Tree(val height: Int, val x: Int, val y: Int, val isVisible: Boolean, val scenicScore: Int) {
        private fun isVisible(treeLine: TreeLine, axis: (Tree) -> Int): Tree {
            val direction1 = sortedTreeLine(treeLine, axis).takeWhile { inFrontOf(it, axis) }.none { blocksView(it) }
            val direction2 = sortedTreeLine(treeLine, axis).takeLastWhile { behindOf(it, axis) }.none { blocksView(it) }
            return Tree(height, x, y, isVisible || direction1 || direction2, scenicScore)
        }

        fun isVisibleHorizontally(treeLine: TreeLine): Tree = isVisible(treeLine) { it.x }
        fun isVisibleVertically(treeLine: TreeLine): Tree = isVisible(treeLine) { it.y }

        private fun blocksView(otherTree: Tree) = otherTree.height >= height

        private fun sortedTreeLine(treeLine: TreeLine, by: (Tree) -> Int) = treeLine.trees.sortedBy(by)
        private fun inFrontOf(otherTree: Tree, axis: (Tree) -> Int) = axis(otherTree) < axis(this)
        private fun behindOf(otherTree: Tree, axis: (Tree) -> Int) = axis(otherTree) > axis(this)


        private fun mapScenicScore(treeLine: TreeLine, axis: (Tree) -> Int): Tree {
            val numberOfVisibleTreesLookingInFront =
                sortedTreeLine(treeLine, axis).takeWhile { inFrontOf(it, axis) }.reversed().fold(Pair(0, false), foldVisibleTreesToAmount()).first
            val numberOfVisibleTreesLookingInBehind =
                sortedTreeLine(treeLine, axis).takeLastWhile { behindOf(it, axis) }.fold(Pair(0, false), foldVisibleTreesToAmount()).first
            return Tree(height, x, y, isVisible, scenicScore * numberOfVisibleTreesLookingInFront * numberOfVisibleTreesLookingInBehind)
        }

        fun mapHorizontalScenicScore(treeLine: TreeLine): Tree = mapScenicScore(treeLine) { it.x }
        fun mapVerticalScenicScore(treeLine: TreeLine): Tree = mapScenicScore(treeLine) { it.y }

        private fun foldVisibleTreesToAmount(): (Pair<Int, Boolean>, Tree) -> Pair<Int, Boolean> = { seeAmount, tree ->
            when {
                seeAmount.second -> seeAmount
                tree.height >= height -> Pair(seeAmount.first + 1, true)
                else -> Pair(seeAmount.first + 1, false)
            }
        }
    }


    fun implementP1(input: String): Int {
        return dh.lines(input).foldIndexed(TreeGrid(emptySet())) { index, grid, line -> grid.addTrees(line, index) }.numberOfTreesVisible()
    }


    fun implementP2(input: String): Int {
        return dh.lines(input).foldIndexed(TreeGrid(emptySet())) { index, grid, line -> grid.addTrees(line, index) }.bestScenicScore()
    }

    @Test
    fun runp1() {
        Assertions.assertThat(implementP1(ei)).isEqualTo(eo1)
        println(implementP1(AOCAssignmentReader().readInputForDay(day)))
    }

    @Test
    fun runp2() {
        Assertions.assertThat(implementP2(ei)).isEqualTo(eo2)
        println(implementP2(AOCAssignmentReader().readInputForDay(day)))
    }
}
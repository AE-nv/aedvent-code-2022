package day7

sealed trait Tree {
  val name: String
  var parent: Option[Tree]
  var children: Seq[Tree]

  override def toString: String = s"Node/Leaf ${this.name} with ${this.children.size} children"
}
case class Node(name: String, var children: Seq[Tree], var parent: Option[Tree]) extends Tree
case class Leaf(name: String, size: Int, var parent: Option[Tree], var children: Seq[Tree] = Seq.empty) extends Tree

package com.github.sorhus.uja

case class Edge(n1: String, n2: String) extends Ordered[Edge] {

  override def compare(that: Edge) = hashCode.compare(that.hashCode)
}

case class Node(id: String, url: String) extends Ordered[Node] {

  def compare(that: Node): Int = id.compare(that.id)
}


case class PageView(identifier: String, url: String, timestamp: Long)

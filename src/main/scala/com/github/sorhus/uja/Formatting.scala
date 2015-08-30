package com.github.sorhus.uja

trait EdgeFormatter extends Function[Edge, String]

class DotEdgeFormatter extends EdgeFormatter {
  def apply(edge: Edge): String = s"\t${edge.n1} -> ${edge.n2};"
}

trait NodeFormatter extends Function[(Node, Long), String]

class DotNodeFormatter extends NodeFormatter {

  def apply(input: (Node, Long)): String = input match {
    case (node, count) => s"""\t${node.id} [label="${node.url}: $count"];"""
  }
}

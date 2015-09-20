package com.github.sorhus.uja

import scala.annotation.tailrec

class EdgeFactory extends (Session => Edges) with Serializable {

  def apply(session: Session) = recurse(session.reverse, Nil)

  @tailrec private def recurse(session: Session, edges: Edges): Edges = {
    session match {
      case head :: Nil => edges
      case head :: neck :: tail => recurse(session.tail, Edge(head.id, neck.id) :: edges)
      case Nil => throw new RuntimeException("wtf")
    }
  }
}

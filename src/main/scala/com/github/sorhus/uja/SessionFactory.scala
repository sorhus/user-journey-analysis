package com.github.sorhus.uja

import scala.annotation.tailrec

class SessionFactory extends (Iterator[PageView] => Iterator[Session]) {

  // naive sessions for now
  def apply(pageviews: Iterator[PageView]): Iterator[Session] = {
    Iterator.single(pageviews.toList)
      .map(pageviews => recurse(pageviews, Nil))
  }

  def hash(s: String): String = s"${s.hashCode}"

  @tailrec private def recurse(pageviews: List[PageView], session: Session): Session = {
    pageviews match {
      case Nil => session
      case head :: tail =>
        val prev = session match {
          case Nil => Node("", "")
          case h :: t => h
        }
        recurse(tail, Node(hash(s"${prev.id}${head.url}"), head.url) :: session)
    }
  }
}

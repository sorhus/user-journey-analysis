package com.github.sorhus.uja.scalding

import com.github.sorhus.uja._
import com.twitter.scalding._

// Output:
// 1. Distinct List of Nodes with count
// 2. Distinct List of Edges
class UserJourneyAnalysis(args: Args) extends Job(args) {

  val input = args("input")
  val nodesOutput = args("output-nodes")
  val edgesOutput = args("output-edges")

  val reader = {
    val separator = args.getOrElse("separator", "\t")
    val timestamp = args.getOrElse("timestamp", "0").toInt
    val id = args.getOrElse("identifier", "1").toInt
    val url = args.getOrElse("url", "2").toInt
    new SimpleLogReader(separator, id, url, timestamp)
  }

  val hashed: TypedPipe[Session] = TypedPipe.from(TextLine(input))
    .map(reader)
    .groupBy(_.identifier)
    .sortBy(_.timestamp)
    .mapValueStream[Session](new SessionFactory)
    .values
    .forceToDisk

  hashed.flatMap(new EdgeFactory)
    .distinct
    .map(new DotEdgeFormatter)
    .write(TypedTsv(edgesOutput))

  hashed.flatten
    .groupBy(identity)
    .size
    .map(new DotNodeFormatter)
    .write(TypedTsv(nodesOutput))

}



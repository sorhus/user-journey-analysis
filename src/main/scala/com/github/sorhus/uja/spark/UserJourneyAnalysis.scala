package com.github.sorhus.uja.spark

import com.github.sorhus.uja._
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object UserJourneyAnalysis {

  def main(args: Array[String]) {

    val input = args(0)
    val nodesOutput = args(1)
    val edgesOutput = args(2)

    val reader = new SimpleLogReader("\t", 1, 2, 0)

    val conf = new SparkConf()
    val sc = new SparkContext(conf)

    val sessionFactory = new SessionFactory

    val hashed: RDD[List[Node]] = sc.textFile(input, 2).cache()
      .map(reader)
      .groupBy(_.identifier)
      .flatMapValues{ pageviews =>
        // hack around the spark api and hope it fits on heap
        val sorted = pageviews.toList.sortBy(_.timestamp).toIterator
        sessionFactory.apply(sorted)
      }
      .values
      .cache()

    hashed.flatMap(new EdgeFactory)
      .distinct()
      .map(new DotEdgeFormatter)
      .saveAsTextFile(edgesOutput)

    hashed.flatMap(identity)
      .groupBy(identity)
      .mapValues(_.size.toLong)
      .map(new DotNodeFormatter)
      .saveAsTextFile(nodesOutput)
  }
}
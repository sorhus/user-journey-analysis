import scala.annotation.tailrec
import util.Random
import java.util.UUID

val n = args(0).toInt // number of sessions to generate
val journeys = Array(
  List("www.example.com","www.example.com/a","www.example.com/a/b","www.example.com/a/b/c","www.example.com/a/b/c/d"),
  List("www.example.com","www.example.com/a","www.example.com/b","www.example.com/c","www.example.com/d"),
  List("www.example.com","www.example.com/a","www.example.com/a/a","www.example.com/a/b","www.example.com/a/c","www.example.com/a/d")
)

@tailrec def get(urls: List[String], result: List[String] = Nil): List[String] = {
  if (urls.isEmpty || (result.nonEmpty && Random.nextBoolean())) {
    result.reverse
  } else {
    get(urls.tail, urls.head :: result)
  }
}

Range(0, n).foreach{_ =>
  val id = UUID.randomUUID
  val urls = get(journeys(Random.nextInt(journeys.length)))
  urls.zipWithIndex.foreach{case(url, i) =>
    println(s"${i + System.currentTimeMillis()}\t$id\t$url")
  }
}


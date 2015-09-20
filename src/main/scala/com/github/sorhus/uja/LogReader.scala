package com.github.sorhus.uja

trait LogReader extends (String => PageView) with Serializable

class SimpleLogReader(separator: String, identifier: Int, url: Int, timestamp: Int) extends LogReader {
  def apply(line: String): PageView = {
    val split = line.split(separator)
    PageView(split(identifier), split(url), split(timestamp).toLong)
  }
}

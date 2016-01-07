package com.github.sorhus.uja

import com.twitter.scalding._

class OrcTest(args: Args) extends Job(args) {

  TypedPipe.from("a" :: "b" :: " c" :: Nil)
    .write(ORC(args("output")))
}
package com.yclian.akka.hello

import java.lang.Thread.sleep

object Main {

  def main(args: Array[String]): Unit = {
    akka.Main.main(Array(classOf[Hello].getName))
  }

}

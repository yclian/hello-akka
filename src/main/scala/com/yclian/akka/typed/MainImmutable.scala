package com.yclian.akka.typed

import akka.typed.ActorSystem
import akka.typed.scaladsl.Actor

import scala.io.StdIn

/**
  * @see https://akka.io/blog/2017/05/05/typed-intro
  * @see https://github.com/patriknw/akka-typed-blog/blob/master/src/main/scala/blog/typed/scaladsl/Greeter1.scala
  */
object MainImmutable {

  def main(args: Array[String]): Unit = {

    val root = Actor.deferred[Nothing] { ctx =>

      import GreeterImmutable._
      val greeter = ctx.spawn(greeterBehavior, "greeter")
      greeter ! Who("Sandy Hook")
      greeter ! Greet

      Actor.empty
    }

    val system = ActorSystem[Nothing](root, "HelloWorld")
    try {
      println("Press ENTER to exit the system")
      StdIn.readLine()
    } finally {
      system.terminate()
    }
  }
}

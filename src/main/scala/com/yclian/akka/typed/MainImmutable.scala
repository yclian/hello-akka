package com.yclian.akka.typed

import akka.typed.{ActorRef, ActorSystem}
import akka.typed.scaladsl.Actor

import scala.io.StdIn

/**
  * @see https://akka.io/blog/2017/05/05/typed-intro
  * @see https://github.com/patriknw/akka-typed-blog/blob/master/src/main/scala/blog/typed/scaladsl/Greeter1.scala
  */
object MainImmutable {

  def main(args: Array[String]): Unit = {

    // Actor.deferred is like a factory for a behavior. Creation of the behavior instance is deferred until the actor
    // is started, as opposed to Actor.immutable that creates the behavior instance immediately before the actor is
    // running.
    val root = Actor.deferred[Nothing] { ctx =>

      import GreeterImmutable._
      // The actor reference is typed, ActorRef[Command] so only messages implementing Greeter2.Command such as
      // WhoToGreet and Greet can be sent via that ActorRef. Being able to use accurately typed actor references
      // everywhere is the main goal of Akka Typed.
      val greeter: ActorRef[Command] = ctx.spawn(greeterBehavior, "greeter")
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

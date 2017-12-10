package com.yclian.akka.hello

import akka.actor.Actor

object Greeter {

  case object Greet
  case object Done
  final case class Who(who: String)

}

class Greeter extends Actor {

  import Greeter._

  private var msg = "Hello"

  def receive = {
    case Who(who) =>
      msg = s"Hello $who!"
    case Greet =>
      println(msg)
      sender() ! Greeter.Done
  }
}

package com.yclian.akka.typed

import akka.typed.Behavior
import akka.typed.scaladsl.Actor

object Greeter {

  sealed trait Command
  case object Greet extends Command
  case object Done extends Command
  final case class Who(who: String) extends Command

  val greeterBehavior: Behavior[Command] =
    Actor.mutable[Command](ctx => new Greeter)
}

class Greeter extends Actor.MutableBehavior[Greeter.Command] {

  import Greeter._

  private var greeting = "hello"

  override def onMessage(msg: Command): Behavior[Command] = {
    msg match {
      case Who(who) =>
        greeting = s"Hello $who"
      case Greet =>
        println(greeting)
    }
    this
  }
}

package com.yclian.akka.typed

import akka.typed.Behavior
import akka.typed.scaladsl.Actor

object GreeterImmutable {

  sealed trait Command
  case object Greet extends Command
  final case class Who(who: String) extends Command

  val greeterBehavior: Behavior[Command] = greeterBehavior(currentGreeting = "hello")

  /**
   * Note that with the immutable style there is no enclosing class for the actor. The actor is essentially defined as
   * a function from message to next behavior (T => Behavior[T]). To keep the same behavior you return Actor.same.
   *
   * @see http://docs.scala-lang.org/sips/completed/named-and-default-arguments.html
   */
  private def greeterBehavior(currentGreeting: String): Behavior[Command] =
    Actor.immutable[Command] { (ctx, msg) =>
      msg match {
        case Who(who) =>
          greeterBehavior(s"Hello $who")
        case Greet =>
          println(currentGreeting)
          Actor.same
      }
    }

}

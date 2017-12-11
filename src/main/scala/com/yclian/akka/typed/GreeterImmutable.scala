package com.yclian.akka.typed

import akka.typed.Behavior
import akka.typed.scaladsl.Actor

object GreeterImmutable {

  /** When we define an algebraic data type using sealed traits we allow the compiler to perform exhaustiveness checking.
   * In simpler words, this means the compiler will shout at us if we miss out a case in our structural recursion.
    *
   * @see https://underscore.io/blog/posts/2015/06/02/everything-about-sealed.html
   */
  sealed trait Command
  case object Greet extends Command
  case object Foo extends Command
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
      // Total function: you have to handle all incoming message types.
      msg match {
        case Who(who) =>
          greeterBehavior(s"Hello $who")
        case Greet =>
          println(currentGreeting)
          Actor.same
      }
    }

}

package com.yclian.akka.hello

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Terminated}

/**
  * @see https://www.lightbend.com/activator/template/akka-sample-main-scala
  */
object Main2 {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem()
    system.actorOf(
      Props(classOf[Terminator], system.actorOf(Props[Hello], "helloWorld")),
      "terminator"
    )
  }

}

class Terminator(ref: ActorRef) extends Actor with ActorLogging {

  context watch ref

  def receive = {
    case Terminated(_) =>
      log.info("{} has terminated, shutting down..!", ref.path)
      context.system.terminate()
  }
}

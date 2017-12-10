package com.yclian.akka.hello

import akka.actor.{Actor, Props}

class Hello extends Actor {

  override def preStart(): Unit = {
    val greeter = context.actorOf(Props[Greeter], "greeter")
    greeter ! Greeter.Who("YC")
    greeter ! Greeter.Greet
  }

  override def receive = {
    case Greeter.Done =>
      println("Bye world! </3")
      context.stop(self)
  }
}
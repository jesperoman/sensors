package se.oeman

import akka.actor.{ActorSystem, Props}

object Remote extends App {
  println("Creating actor system...")
  val system = ActorSystem.create("SensorReceiverSystem")
  println("Creating receiving actor...")
  val receiver = system.actorOf(Props[SensorReceiverActor], name = "receiver")
}

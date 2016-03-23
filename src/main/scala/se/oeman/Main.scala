package se.oeman

import akka.actor.{ActorSystem, Props}

object Main extends App {
  val system = ActorSystem.create("SensorSystem")
  val receiver = system.actorOf(Props.create(classOf[SensorReceiverActor]))
  val sender = system.actorOf(Props.create(classOf[SensorActor], receiver))
}

package se.oeman

import akka.actor.{ActorSystem, Props}

import scala.util.Try

object Main extends App {
  println("Creating actor system...")
  val system = ActorSystem.create("SensorSystem")
  println("Creating receiving actor...")
  val receiver = system.actorOf(Props.create(classOf[SensorReceiverActor]))
  val jnaCallback = Try(new ScalaJNACallback())
  println("Instantiating sensor actor...")
  val sender = system.actorOf(Props.create(classOf[SensorActor], jnaCallback.toOption, receiver))
}

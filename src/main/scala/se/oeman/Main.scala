package se.oeman

import akka.actor.{ActorSystem, Props}

object Main extends App {
  println("Creating actor system...")
  val system = ActorSystem.create("SensorSystem")
  println("Creating receiving actor...")
  val receiver = system.actorSelection("akka.tcp://actorSystemName@127.0.0.1:2552/SensorReceiverSystem/SensorReceiverActor")
//  val receiver = system.actorOf(Props.create(classOf[SensorReceiverActor]))
  println("Instantiating sensor actor...")
  val sender = system.actorOf(Props.create(classOf[JNACallbackActor], receiver))
}

package se.oeman

import akka.actor.{ActorSystem, Props}

object Sensor extends App {
  val remoteIp = args(0)
  println(s"Sending temperature to $remoteIp")
  println("Creating actor system...")
  val system = ActorSystem.create("SensorSystem")
  println("Creating receiving actor...")
  val receiver = system.actorSelection(s"akka.tcp://SensorReceiverSystem@$remoteIp:2552/user/receiver")
//  val receiver = system.actorOf(Props.create(classOf[SensorReceiverActor]))
  println("Instantiating sensor actor...")
  val sender = system.actorOf(Props.create(classOf[JNACallbackActor], receiver))
}

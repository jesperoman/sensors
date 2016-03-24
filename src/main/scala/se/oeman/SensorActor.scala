package se.oeman

import akka.actor.{Actor, ActorRef}


class SensorActor(ref: ActorRef) extends Actor {
  val sensorsjnacallback = new SensorsJNACallback(ref, self)

  override def preStart = {
    println("Starting sensor listener")
    sensorsjnacallback.startListening()
  }

  override def postStop = sensorsjnacallback.stopListening()

  override def receive = {
    case x => println(s"What?: $x")
  }
}

package se.oeman

import akka.actor.Actor

class SensorReceiverActor extends Actor {
  override def receive = {
    case x: String =>
      println(x)
  }
}

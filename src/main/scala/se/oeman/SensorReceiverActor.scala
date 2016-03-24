package se.oeman

import akka.actor.{ActorLogging, Actor}

class SensorReceiverActor extends Actor with ActorLogging {
  override def receive = {
    case Temperature(id, t, p, m, d) =>
      println(s"$d Id: $id, Sensor: $p $m Temperature: ${t}C")
    case Humidity(id, h, p, m, d) =>
      println(s"$d Id: $id, Sensor: $p $m Humidity: ${h}%")
    case x =>
      s"Unexpected message: $x"
  }
}

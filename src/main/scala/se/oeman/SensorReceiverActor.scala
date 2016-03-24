package se.oeman

import akka.actor.Actor

class SensorReceiverActor extends Actor {
  override def receive = {
    case Temperature(t, p, m, d) =>
      println(s"$d Sensor: $p $m Temperature: ${t}C")
    case Humidity(h, p, m, d) =>
      println(s"$d Sensor: $p $m Humidity: ${h}%")
  }
}

package se.oeman

import akka.actor.Actor

class SensorReceiverActor extends Actor {
  override def receive = {
    case Temperature(t, p, m, d) =>
      println(s"Sensor: $p $m")
      println(s"At: $d")
      println(s"Temperature: ${t}C")
    case Humidity(h, p, m, d) =>
      println(s"Sensor: $p $m")
      println(s"At: $d")
      println(s"Humidity: ${h}%")
  }
}

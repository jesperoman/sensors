package se.oeman

import java.util.Date

sealed trait SensorValue {
  val protocol: String
  val model: String
  val date: Date
}

case class Temperature(
  temp: String, protocol: String, model: String, date: Date
) extends SensorValue

case class Humidity(
  level: String, protocol: String, model: String, date: Date
) extends SensorValue

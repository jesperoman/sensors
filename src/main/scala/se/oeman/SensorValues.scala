package se.oeman

import java.util.Date

sealed trait SensorValue {
  val id: Int
  val protocol: String
  val model: String
  val date: Date
}

case class Temperature(
  id: Int, temp: String, protocol: String, model: String, date: Date
) extends SensorValue

case class Humidity(
  id: Int, level: String, protocol: String, model: String, date: Date
) extends SensorValue

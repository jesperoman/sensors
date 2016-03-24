package se.oeman

sealed trait TDModels

case class Protocol(value: String) extends TDModels
case class Model(value: String) extends TDModels
case class SensorId(value: Int) extends TDModels

package se.oeman

import java.util.Date

import akka.actor.{Actor, ActorSelection}
import com.sun.jna.{Native, Pointer}

class JNACallbackActor(receiver: ActorSelection) extends Actor {
  val lib = Native.loadLibrary("libtelldus-core.so.2", classOf[CLibrary]).asInstanceOf[CLibrary]
  lib.tdInit()
  val callback = new CLibrary.SensorCallback() {
    def callbackfunction(
      protocol: Pointer,
      model: Pointer,
      id: Int,
      dataType: Int,
      value: Pointer,
      timestamp: Int,
      callbackId: Int,
      context: Pointer
    ) = {
      val m: String = model.getString(0)
      val p: String = protocol.getString(0)
      val date: Date = new Date(timestamp.toLong * 1000)
      dataType match {
        case CLibrary.TELLSTICK_HUMIDITY =>
          println("Sending value...")
          receiver ! s"$date Id: $id, Sensor: $p $m Humidity: ${value.getString(0)}%"
        case CLibrary.TELLSTICK_TEMPERATURE =>
          println("Sending value...")
          receiver ! s"$date Id: $id, Sensor: $p $m Temperature: ${value.getString(0)}C"
      }
    }
  }
  val callbackID = lib.tdRegisterSensorEvent(callback, null)

  override def postStop = {
    lib.tdUnregisterCallback(callbackID)
    lib.tdClose()
  }

  override def receive = {
    case x => println(x)
  }
}

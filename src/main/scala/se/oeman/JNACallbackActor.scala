package se.oeman

import java.util.Date

import akka.actor.{ActorRef, Actor}
import com.sun.jna.{Pointer, Native}

class JNACallbackActor(receiver: ActorRef) extends Actor {
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
          receiver ! Humidity(id, value.getString(0), p, m, date)
        case CLibrary.TELLSTICK_TEMPERATURE =>
          receiver ! Temperature(id, value.getString(0), p, m, date)
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

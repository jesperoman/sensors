package se.oeman

import akka.actor.ActorRef
import com.sun.jna.Native
import com.sun.jna.Pointer
import java.util.Date

class ScalaJNACallback {
  var callbackID: Int = 0
  var callback: CLibrary.SensorCallback = null
  val lib = Native.loadLibrary("libtelldus-core.so.2", classOf[CLibrary]).asInstanceOf[CLibrary]

  def startListening(receiver: ActorRef) {
    lib.tdInit()
    val callback: CLibrary.SensorCallback = new CLibrary.SensorCallback() {
      def callbackfunction(protocol: Pointer, model: Pointer, id: Int, dataType: Int, value: Pointer, timestamp: Int, callbackId: Int, context: Pointer) {
        val m: String = model.getString(0)
        val p: String = protocol.getString(0)
        val timestampvalue: Long = timestamp.toLong * 1000
        val date: Date = new Date(timestampvalue)
        if (dataType == CLibrary.TELLSTICK_TEMPERATURE) {
          val temp: String = value.getString(0)
          receiver ! Temperature(temp, p, m, date)
        }
        else if (dataType == CLibrary.TELLSTICK_HUMIDITY) {
          val humidity: String = value.getString(0)
          receiver ! Humidity(humidity, p, m, date)
        }
      }
    }
    callbackID = lib.tdRegisterSensorEvent(callback, null)
    while (true) {
      try {
        Thread.sleep(1000)
      }
      catch {
        case e: InterruptedException => {
          println("Interrupted exception!")
          System.exit(0)
        }
      }
    }
  }

  def stopListening {
    System.out.println("Exiting")
    lib.tdUnregisterCallback(callbackID)
    callback = null
    lib.tdClose
  }
}

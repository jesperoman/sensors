package se.oeman;

import akka.actor.ActorRef;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.util.Date;

public class SensorsJNACallback {

  int callbackID;
  CLibrary lib;
  CLibrary.SensorCallback callback;


  public SensorsJNACallback() {
    lib = (CLibrary) Native.loadLibrary("libtelldus-core.so.2", CLibrary.class);
  }

  public void startListening(ActorRef receiver) {

    lib.tdInit();

    //save reference to callback to avoid garbage collection
    CLibrary.SensorCallback callback = new CLibrary.SensorCallback() {
      public void callbackfunction(
        Pointer protocol, Pointer model, int id,
        int dataType, Pointer value,
        int timestamp, int callbackId,
        Pointer context
      ) {
        final String m = model.getString(0);
        final String p = protocol.getString(0);
        long timestampvalue = (long) timestamp * 1000;
        Date date = new Date(timestampvalue);

        if (dataType == CLibrary.TELLSTICK_TEMPERATURE) {
          final String temp = value.getString(0);
          receiver.tell(new Temperature(temp, p, m, date), null);
        } else if (dataType == CLibrary.TELLSTICK_HUMIDITY) {
          final String humidity = value.getString(0);
          receiver.tell(new Humidity(humidity, p, m, date), null);
        }
      }
    };

    //register callback function for sensor events
    callbackID = lib.tdRegisterSensorEvent(callback, (Pointer) null);

    while (true) {
      try {
        //just wait for sensor callbacks
        Thread.currentThread().sleep(1000);
      } catch (InterruptedException e) {
        System.out.println("Interrupted exception!");
        System.exit(0);
      }
    }
  }

  public void stopListening() {
    System.out.println("Exiting");
    lib.tdUnregisterCallback(callbackID);
    callback = null;
    lib.tdClose();
  }
}

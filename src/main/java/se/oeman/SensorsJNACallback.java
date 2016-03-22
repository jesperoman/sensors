package se.oeman;

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

  public void startListening() {

    lib.tdInit();

    //save reference to callback to avoid garbage collection
    CLibrary.SensorCallback callback = new CLibrary.SensorCallback() {
      public void callbackfunction(
        Pointer protocol, Pointer model, int id,
        int dataType, Pointer value,
        int timestamp, int callbackId,
        Pointer context
      ) {
        System.out.println("Sensor: " + protocol.getString(0) + " " + model.getString(0));
        long timestampvalue = (long) timestamp * 1000;
        Date date = new Date(timestampvalue);

        if (dataType == CLibrary.TELLSTICK_TEMPERATURE) {
          System.out.println("Temperature: " + value.getString(0) + "C, " + date.toString());
        } else if (dataType == CLibrary.TELLSTICK_HUMIDITY) {
          System.out.println("Humidity: " + value.getString(0) + "%, " + date.toString());
        }
        System.out.println("");
      }
    };

    //register callback function for sensor events
    callbackID = lib.tdRegisterSensorEvent(callback, (Pointer) null);

    while (true) {
      try {
        //just wait for sensor callbacks
        Thread.currentThread().sleep(1000);
      } catch (InterruptedException e) {
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

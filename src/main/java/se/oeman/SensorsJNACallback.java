package se.oeman;

import akka.actor.ActorRef;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.util.Date;

public class SensorsJNACallback {

  int callbackID;
  CLibrary lib;
  CLibrary.SensorCallback callback;
  private final ActorRef receiver;
  private final ActorRef sender;


  public SensorsJNACallback(ActorRef receiver, ActorRef sender) {
    lib = (CLibrary) Native.loadLibrary("libtelldus-core.so.2", CLibrary.class);
    this.receiver = receiver;
    this.sender = sender;
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
          String temp = value.getString(0);
          System.out.println("Temperature: " + temp + "C, " + date.toString());
          receiver.tell(new SensorData.Temperature(temp), sender);
        } else if (dataType == CLibrary.TELLSTICK_HUMIDITY) {
          String humidity = value.getString(0);
          System.out.println("Humidity: " + humidity + "%, " + date.toString());
          receiver.tell(new SensorData.Humidity(humidity), sender);
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

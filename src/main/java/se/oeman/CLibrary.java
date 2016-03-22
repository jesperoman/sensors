package se.oeman;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface CLibrary extends Library {

  static int TELLSTICK_TEMPERATURE = 1;
  static int TELLSTICK_HUMIDITY = 2;

  void tdInit();

  void tdClose();

  int tdRegisterSensorEvent(SensorCallback function, Pointer context);

  int tdUnregisterCallback(int callbackID);

  public interface SensorCallback extends Callback {
    public void callbackfunction(
      Pointer protocol, Pointer model, int id,
      int dataType, Pointer value,
      int timestamp, int callbackId,
      Pointer context
    );
  }
}


package se.oeman;

import java.io.Serializable;

public class SensorData {

  static class Humidity implements Serializable {
    private static final long serialVersionId = 1L;
    private final String humidity;

    public Humidity(String humidity) {
      this.humidity = humidity;
    }

    public String getHumidity() { return humidity; }
  }

  static class Temperature implements Serializable {
    private final String temp;
    public Temperature(String temp) {
      this.temp = temp;
    }
    public String getTemp() { return temp; }
  }

}

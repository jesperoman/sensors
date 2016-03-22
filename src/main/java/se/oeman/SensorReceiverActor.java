package se.oeman;

import akka.actor.UntypedActor;

public class SensorReceiverActor extends UntypedActor {

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof SensorData.Temperature) {
      SensorData.Temperature t = (SensorData.Temperature) message;
      System.out.println("Received temperature:" + t.getTemp());
    } else if (message instanceof SensorData.Humidity) {
      SensorData.Humidity h = (SensorData.Humidity) message;
      System.out.println("Received humidity:" + h.getHumidity());
    }
  }
}

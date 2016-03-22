package se.oeman;

public class SensorListener {
  public static void main(String [] args) {
    final SensorsJNACallback sensorsjnacallback = new SensorsJNACallback();

    //add shutdown hook to allow callback unregistration
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        sensorsjnacallback.stopListening();
        Runtime.getRuntime().halt(0);
      }
    });

    sensorsjnacallback.startListening();

    System.out.println("Guess we never get here?");

  }
}

package se.oeman;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class SensorActor extends UntypedActor {
  SensorsJNACallback sensorsjnacallback;

  public SensorActor(ActorRef ref) {
    sensorsjnacallback = new SensorsJNACallback(ref, getSelf());
  }

  @Override
  public void preStart() {
    sensorsjnacallback.startListening();
  }

  @Override
  public void postStop() {
    sensorsjnacallback.stopListening();
  }

  @Override
  public void onReceive(Object message) throws Exception {
    System.out.println(message);
  }
}


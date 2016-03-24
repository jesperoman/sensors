package se.oeman

import akka.actor.{ActorRef, Actor}
import scala.concurrent.duration._

class SensorActor(jnaCallBack: Option[ScalaJNACallback], receiver: ActorRef) extends Actor {

  override def preStart = {
    println("Starting sensor listener")
    jnaCallBack.foreach(_.startListening(receiver))
    if (jnaCallBack.isEmpty) scheduleSend()
  }

  def scheduleSend() = {
    println("scheduling sending")
    import context.dispatcher
    context.system.scheduler.schedule(0 milliseconds, 1000 milliseconds) {
      receiver ! "No tellstick connected"
    }
  }

  override def postStop = jnaCallBack.foreach(_.stopListening)

  override def receive = {
    case x => println("Huh?")
  }
}

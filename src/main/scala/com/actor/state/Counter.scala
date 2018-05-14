package com.actor.state

import akka.actor._

case class SimpleMessage(msg: String)
case class CountTick()

class Counter extends Actor {
  var count: Int = 0
  override def receive: Receive = {
    case SimpleMessage(msg) => println(msg)
    case CountTick => {
      count = count + 1
      println(count)
    }
  }
}


object CounterApp extends App {

  val system = ActorSystem("countActor")

  val counter = system.actorOf(Props[Counter], "counter")

  counter ! SimpleMessage("hi")

  counter ! CountTick

  counter ! CountTick

  counter ! CountTick

  system.terminate()

}
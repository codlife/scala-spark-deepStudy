package akkas.wordCount

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.duration._
import akka.dispatch.ExecutionContexts._

/**
  * Created by wjf on 16-10-2.
  */
object WordCountMain {


  implicit val ec = global
  def main(args: Array[String]): Unit = {

    val system = ActorSystem()
    val actor = system.actorOf(Props(new WordCounterActor(args(0))))
    implicit val timeout = Timeout(25 seconds)

    val future = actor ? StartProcessFileMsg()
    future.map { result =>
      println("Total number of words " + result)
      system.terminate()
    }
  }
}

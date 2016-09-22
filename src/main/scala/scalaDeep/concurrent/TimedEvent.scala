package scalaDeep.concurrent

import java.util.{Timer, TimerTask}

import scala.concurrent.{Future, Promise}

/**
  * Created by wjf on 16-9-22.
  */
object TimedEvent {
  def main(args: Array[String]): Unit = {
    val timer = new Timer
    def delayedSuccess[T](secs:Int, value: T): Future[T] = {
      val result = Promise[T]
      timer.schedule(new TimerTask() {
        def run(): Unit = {
          result.success(value)
        }
      }, secs * 1000)
      result.future
    }
    def delayedFailture(secs: Int, msg: String):Future[Int] = {
      val result = Promise[Int]
      timer.schedule(new TimerTask(){
        def run():Unit = {
          result.failure(new IllegalArgumentException(msg))
          println("this is failture")
        }
      }, secs *1000)
      result.future
    }
  }
}

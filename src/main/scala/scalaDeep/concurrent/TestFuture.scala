package scalaDeep.concurrent

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by wjf on 16-9-22.
  */
object TestFuture {
  def main(args: Array[String]): Unit = {

    val s ="Hello"
    val f:Future[String] = Future {
      s + "Future"
    }
//    f onSuccess {
//      case msg => println(msg)
//    }
    f onComplete {
      case msg => println("success" + msg)
    }
    println(s)

  }
}

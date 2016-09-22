package scalaDeep.concurrent

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by wjf on 16-9-22.
  */
object TestFuture {
  /*
  * 异步操作的有两个经典接口：Future和Promise,其中的 Future 表示一个可能还没有实际完成的异步任务的结果，
  * 针对这个结果可以添加 Callback 以便在任务执行成功或失败后做出对应的操作，而 Promise 交由任务执行者，
  * 任务执行者通过 Promise 可以标记任务完成或者失败。 可以说这一套模型是很多异步非阻塞架构的基础。
  * scala的Future表示一个异步操作的结果状态，它持有一个值，在未来的某个之间点可用，该值是异步操作的结果,
  * 当异步操作没有完成，那么Future的isCompleted为false,当异步操作完成了且返回了值，那么Future的isCompleted返回true且是success,
   * 如果异步操作没有完成或者异常终止，那么Future的isCompleted也返回true但是是failed.scala的Future有一个重要特性，
   * 就是他只能被赋值一次，一旦Future对象被赋值了或者设置为异常，那么它会变成不可变的
   * 创建一个Future最简单的方式就是调用future函数：它会启动一个异步操作且返回一个包含了操作结果的。
   * 一个future函数完成该值就可以用了，Future[T]的泛型是异步操作结果的类型。下面是一个创建Future的例子。
  * */
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


package scalaBasic

import breeze.numerics.pow

import scala.collection.mutable.LinkedList
import scala.util.Random

/**
  * Created by wjf on 2016/8/14.
  */
object TestList {
  def main(args: Array[String]): Unit = {
    /*
    * 在Scala 中，列表要么是Nil，要么是一个head元素加一个tail，而tail有事一个列表*/
    val digits = List(4, 2)
    digits.head //4
    digits.tail // List(2)
    digits.tail.head //2
    digits.tail.tail // Nil
    // :: 操作符从给定的头和尾创见一个新的列表
    9 :: List(3, 1)
    9 :: 3 :: 1 :: Nil
    // 计算求和 ，模式匹配版本
    def sum(lst: List[Int]): Int = lst match {
      case Nil => 0
      case h :: t => h + sum(t)
    }
    // 递归版,递归之所以那么自然，是因为列表的尾部正好又是一个列表
    def sum2(lst: List[Int]): Int = {
      if (lst == Nil) 0
      else lst.head + sum(lst.tail)
    }

    // 可变列表
    val lst = LinkedList(1, -2, -3)
    var cru = lst
    while (cru != Nil) {
      if (cru.elem < 0) cru.elem = 0
      cru = cru.next
    }
    // 各一个元素删除一个元素
    while (cru != Nil && cru.next != Nil) {
      cru.next = cru.next.next
      cru = cru.next
    }
    println(lst.elem)
    //    lst.next=Nil
    lst.next = LinkedList.empty
    println(lst.elem)
    println(lst.next.elem)

    // flatMap 将结果串接在一起
    val s = List("wjf", "csm")
    def ulcase(t: String) = Vector(t.toUpperCase(), t.toLowerCase())
    s.map(ulcase).foreach(println)
    s.flatMap(ulcase).foreach(println)

    // colect 方法用于偏函数，那些并没有对所有可能输入值进行定义的函数，它产出的所有参数的函数值的集合，例如
    "-3+4".collect { case '+' => 1; case '-' => -1 }.foreach(println)

    // scanLeft scanRight
    (1 to 10).scanLeft(0)(_ + _).foreach(println)

    //拉链操作 zip 得到一个对偶
    val prices = List(1, 2, 3)
    val quantities = List(1, 2, 3)
    prices zip quantities map (p => p._1 * p._2) foreach (println)
    // zipALl 制定缺省值
    List(1, 2, 3) zipAll(List(1, 2), 0.0, 1) foreach (println)

    // 懒视图，应用view 方法来得到类似的效果，该方法产生一个方法总是被懒执行的集合 }
    val powers = (0 until 1000).view.map(pow(10, _)) // 将产生一个未被求值得集合，
    // 当你计算 pow(10,100)时，其他值得幂并没有被计算，和流不同，这些视图并不会缓存任何值，
    // 懒集合的好处，看下面的例子
    //    (0 to 1000).map(pow(10, _)).map(1 / _)
    //(0 to 1000).view.map(pow(10, _)).map(1 / _).force
    // 第二个例子，当求知动作被强制执行时，对于每个元素，这两个操作被同时执行，不需要额外的中间集合

    // 流 对象
    def numsFrom(n: BigInt): Stream[BigInt] = n #:: numsFrom(n + 1)
    val tenOrMore = numsFrom(10)
//    tenOrMore.force
    println(tenOrMore)

    // 并行集合，Scala 提供了用于操纵大型集合任务的诱人的解决方案，这些任务通常可以很自然的并行操作。
    // 举例来讲：要计算所有元素只和，多个线程可以并发的计算不通区域的和；在最后，把结果汇总起来
    for( i <-( 0 until 100).par) print( i + " ")

    var array=for(i  <- (0 until 100).par) yield i + " "
    array.foreach(println)
    var a =new Random()
    for(i <- 0 until 10){
      println(a.nextInt(10))
    }

  }

}

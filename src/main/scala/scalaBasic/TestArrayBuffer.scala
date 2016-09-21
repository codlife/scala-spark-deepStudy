package scalaBasic
import scala.collection.mutable.ArrayBuffer
/**
  * Created by wjf on 2016/8/5.
  */
object TestArrayBuffer {
  def main(args: Array[String]) {
    /*
    *  1:fix length array*/
    val arrStr=Array("wo","cs","you")
    println(arrStr(1))
//    定长数组，不能修改长度
//    arrStr+="s"
    arrStr(0)="ww"
    println(arrStr(0))

    /*
    * 2: variable length array
    * like ArrayList in java but ArrayBuffer
    * is more powerful*/
    val arrBuffer1 =ArrayBuffer[Int]()

    val arrBuffer2=ArrayBuffer(1,3,4,-1,-4)
    arrBuffer1 +=23
    arrBuffer1 += (2,3,4,32)
    arrBuffer1 ++=arrBuffer2
    arrBuffer1.trimEnd(4)
    arrBuffer1.remove(0)
    arrBuffer1.foreach(println)
    val arr=arrBuffer1.toArray
    val arrayBuffer2=arr.toBuffer

    /*
    * traversal  array and buffer is the same*/
    for(i <- 0 until arrBuffer1.length)
      yield arrBuffer1(i)*2
//    arrBuffer1.foreach(println)
    for(i <- 0 until (arrBuffer1.length,2))
      println(arrBuffer1(i))
    for(elem <- arrBuffer1) println(elem)
    println("-----------------")
    arrBuffer1.filter( _ > 4).foreach(println)

    /*
    * common algorithm*/
    arrBuffer1.sum
    Array("w","s").max
//    arrBuffer1.sorted( _ > _)
    arrBuffer1.sortWith(_ > _).foreach(println)
    println( math.signum(1))
    var a=Integer.MAX_VALUE
    var b= -a
    println( a >> 31 | b >>>31)
    println( a>>> 31)
    println( b >> 31)







  }
}

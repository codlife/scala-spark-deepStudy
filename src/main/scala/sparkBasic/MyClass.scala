package sparkBasic

import org.apache.spark.rdd.RDD

/**
  * Created by wjf on 2016/7/12.
  */
object MyClass{
  val field="Hello"
  def doStuff(rdd:RDD[String]):RDD[String]={
    rdd.map(x => field+x)}
  def func1(s:String,i:Int):Unit ={
    s
  }
  def funcOne(s:String):String={
    s
  }
}
class My1 extends Serializable{
  def func1(s:String):String={ s}
  def doStuff(rdd:RDD[String]):RDD[String]={
    rdd.map(func1)
  }
}
class My2 extends Serializable{
  val field="hello"
  def doStuff(rdd:RDD[String]):RDD[String]={
    rdd.map(x=> x+field)
  }
}
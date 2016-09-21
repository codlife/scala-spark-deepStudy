package scalaBasic
import java.util
import java.util.{Comparator, PriorityQueue => JPriorityQueue}

import breeze.linalg.{DenseVector, split}
import org.apache.spark.ml.feature.LabeledPoint
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, SparkSession}

import scala.collection.mutable.{ArrayBuffer, HashMap}
/**
  * Created by wjf on 2016/8/7.
  */
object TestArray {
  def main(args: Array[String]) {
    val a= Set(1,2,(2,3))
    println(a)
    val s= Array[String]("s","w")
    f(s:_*)



  }
  def f(args:String *): Unit ={
    println(args)
  }
  /*
  * 学习要点：
  * 1.若长度固定则使用Array,否则使用ArrayBuffer
  * 2.提供初始值时不要使用 new
  * 3.用()访问元素
  * 4.for(elem <- arr) 遍历元素
  * 5.for(elem <- arr if....) yiled 将原来的数组转化为新数组
  * Scala 数组可以和Java数组相互转化，用ArrayBuffer，使用scala.collection.javaConvertions中的函数转化
  * */
  def testArray: Unit ={
    val nums=new Array[Int](10)
    val s=Array("wjf","csm")
    val b=ArrayBuffer[Int]()
    b+=1
    b+=(1,2,3,4)
    b ++= Array(8,13,21)
    // remove the last 3 elements
    // 在数组缓冲的尾端添加或移除元素非常高效
    b.trimEnd(3)
    b.insert(2,3)

    b.toArray

    val a=Array(1,2,3,4)
    // for(...) yiled 创建一个与原始集合类型相同的新集合
    // 守卫：if ，过滤元素
    /*有些有函数式编程经验的程序员喜欢使用filter 和map而不是使用
    * 守卫和 yiled ，这只是一种风格-for循环所做的事是完全相同的*/
    val reuslt=for(elem <- a) yield 2*elem
    var c=for(elem <- a if elem % 2 == 0) yield 2*elem
    c=a.filter(_ % 2 ==0).map(_*2)
    c=a.filter{_ % 2 ==0} map { _ *2}
  }
  /* 具体案例：一个数组，删除 除第一个负数外的所有负数
  * 对比下面两种实现
  * version1 每次遇见负数就删除，删除的过程效率较低
  * 需要数组元素的移动
  * Version2 首先获取需要保留的元素的下标，然后批量删除*/
  var a=new ArrayBuffer[Int](10)
  var first=true
  def version1: Unit ={
    for(i <- 0 until a.length) {
      if(a(i) < 0){
        if(first) first=false
        else a.remove(i)
      }
    }
  }
  def version2(): Unit ={
    val indexs=for(i <- 0 until a.length if first | a(i)>0) yield{
      if(a(i) < 0) first=false
      i
    }
    for(i <- 0 until indexs.length)a(i)=a(indexs(i))
    a.trimEnd(a.length - indexs.length)
  }

  def testOFDim: Unit ={
    val matrix=Array.ofDim[Double](2,3)
    matrix(1)(1)=2
    val triangle=new Array[Array[Int]](10)
    for(i <- 0 until triangle.length)
      triangle(i)=new Array[Int](i+1)
  }


}


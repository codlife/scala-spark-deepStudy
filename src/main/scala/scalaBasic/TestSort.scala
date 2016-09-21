package scalaBasic

import scala.util.Sorting

/**
  * Created by wjf on 2016/8/5.
  */
object TestSort {
  def main(args: Array[String]) {
    val s=List("a","b","c","b")
    val n=List(2,3,1,2)
    val m=Map(3 -> 2, 2-> 4, 1 -> 3)
    s.sorted.foreach(println)
    implicit val keyOrdering =new Ordering[String]{
      override def compare(x:String,y:String):Int={
        y.compareTo(x)
      }
    }
    n.sorted.foreach(println)
    n.sortWith(_< _).foreach(println)
    s.sortWith(compfn1)

    m.toList.sorted foreach{
      case(key,value) => println(key+ "="+value)
    }
    m.toList.sortBy(_._2).foreach{
      case(key,value) => println(key+ " ="+value)
    }
    /*
    * the sort before have no effect to the source data
    * if the source data is array,we can sort the source data*/
    val a=Array(1,2,3,1,2)
    Sorting.quickSort(a)
    println(a.mkString(","))




  }
  def compfn1(e1:String,e2:String) = (e1 compareToIgnoreCase e2) <0
  def compfn2(e1:String,e2:String) = ( e1.toLowerCase < e2.toLowerCase)

}

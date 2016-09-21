package sparkBasic

import org.apache.spark.rdd.RDD

/**
  * Created by wjf on 2016/7/11.
  */
object ScalaBasic {
  def main(args: Array[String]) {
    ScalaBasic.testFor()
  }
  def testFor(): Unit ={
//    var array:Array[String]=new Array[String](10);
    var array=Array("w","f","j","jj")
//    array=("w";"j";"f")
    for(value: String <- array if value.endsWith("j");if value.length > 1){
      println(value)
    }
    for(i <- 0 until array.length){
      println(array(i))
    }
    array.foreach(println)

    println("---------------")
    var myArray:Array[Array[String]] =new Array[Array[String]](10)
    for(i<- 0 until myArray.length){
      myArray(i)=new Array[String](10)
      for(j<- 0 until myArray(i).length){
        myArray(i)(j)="value is:" +i+","+j
        print(myArray(i)(j))
      }
      println()
    }

    for(anArray: Array[String] <-myArray; innerArray:String <- anArray){
      println(innerArray)
    }

  }
  def max(x:Int)= if(x>=0) x else -x
  def sum(n:Int)={
    var r=1
    for(i<- 1 to 10)
      r=r*i
    // 函数的最后一个表达式就是返回值，如果使用return 则必须制定函数返回值类型
    r
  }

}



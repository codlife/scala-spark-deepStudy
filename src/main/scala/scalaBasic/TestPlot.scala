package scalaBasic

import breeze.linalg.{DenseMatrix, DenseVector}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.HashPartitioner
import scala.collection.mutable.Map

/**
  * Created by wjf on 2016/8/23.
  */
object TestPlot {
  val conf =new SparkConf().setAppName("aggregate").setMaster("local")
  val sc= new SparkContext(conf)

  def setOP(a:Int ,b:String): Int ={

    println("seqOP: "+ a +"\t"+b)
     a
  }
  def comOP(a:Int ,b:Int): Int ={
   println("comOP: "+ b+ "\t"+a)
    a*b
  }
  // 输出结果 7
  /* 首先 list 分为两个分区 123， 456
  *  然后进行 setOP 操作
  * 第一个分区： 输出 3,1  1,2 1,3
  * 第二个分区： 输出 3,4  3,5 3,6
  *  最后进行 comOP 操作
  *  输出 3 1
  *      4 3
  *  最后的值为： 3+ 1+ 3 =7
   *  */
  def testAggregate(): Unit ={
    val z =sc.parallelize( List(1,2,3))

    println( z.aggregate("a")(
      seqOp = (c,v) => {
        println(v+1)
        (c)
      },
      combOp = (c,v) =>{
        (v)
      }
    ))



  }

  def testFold(): Unit ={
    val z= sc.parallelize(List(1,2,3,4),2)
    println(z.fold(1)(comOP))
  }

  def testLookup(): Unit ={
    val z=sc.makeRDD(Array(("a",1),("a",2),("c",3)))
    println(z.lookup("a"))
  }

  def testReduce(): Unit ={
    val rdd1=sc.makeRDD(1 to 10,2)
    println(rdd1.reduce(_ + _))
    val rdd2 =sc.makeRDD(Array(("a",1),("b",2),("c",3)))
    println(rdd2.reduce((x ,y) => {
      (x._1 + y._1, x._2 + y._2)
      //输出abc 6
    }))
  }
  def testCollect(): Unit ={
    val rdd1 =sc.makeRDD(1 to 10,2)

    println(rdd1.collect())
  }
  def testForeach(): Unit ={
    var cnt=sc.doubleAccumulator("0")
    var rdd1= sc.makeRDD(1 to 10,2)
    println(rdd1.foreach( x=> cnt.add(x)))
    println(cnt.value)
  }

  def testPartition(): Unit ={
    val rdd1= sc.makeRDD(Array((1,"A"),(2,"B"),(3,"C"),(4,"D")),3)
    println(rdd1.partitions.size)
    val result= rdd1.mapPartitionsWithIndex{(partID,iter) =>{
     val part_map= Map[String,List[(Int,String)]]()
      while(iter.hasNext){
        val part_name= "part_"+partID
        val elem = iter.next()
        if(part_map.contains(part_name)){
          var elems = part_map(part_name)
          elems ::= elem
          part_map(part_name)= elems
        }
        else{
          part_map(part_name) =List[(Int,String)](elem)
        }
      }
      part_map.iterator
    }
    }.collect()
    rdd1.partitions.map(p => println(p.index))

    result.foreach(println)
//    rdd1.foreach(println)

    // test partiotionBY
    var rdd2= rdd1.partitionBy(new HashPartitioner(2))
    println(rdd2.partitions.size)
  }
  def testMapValues(): Unit ={
    var rdd1= sc.makeRDD(Array((1,"A"),(2,"B"),(3,"C"),(4,"D")),2)
    rdd1.mapValues( x => x+ "_").collect.foreach(println)
    rdd1.flatMapValues( x => x+ "_").collect().foreach(println)

  }

  def testCobmineByKey(): Unit ={
    var rdd1= sc.makeRDD(Array(("A",1),("A",3),("A",2),("A",1),("A",2),("C",1)),2)
    var rdd2 =rdd1
    rdd1.combineByKey(
      (v:Int) => v + "_",
      (c:String,v:Int) => c+"@"+v,
      (c1:String,c2:String) => c1+ "$" +c2
    ).collect.foreach(println)

    rdd2.combineByKey(
      (v:Int) => List(v),
      (c:List[Int],v:Int) => v::c,
      (c1:List[Int],c2:List[Int]) => List(1)
    ).collect.foreach(println)
  }
  def testFoldBYKey(): Unit ={
    val rdd2 =sc.makeRDD(Array(("A",1),("A",2),("c",1),("b",1),("b",2)))
    rdd2.foldByKey(2)(_+_).collect().foreach(println)

  }
  def testCogroup(): Unit ={
    //类似于 全外关联 full outer join
    var rdd1 = sc.makeRDD(Array(("A",1),("B",2),("C",3)),2)
    var rdd2 = sc.makeRDD(Array(("A","a"),("C","c"),("D","d")),2)
    var rdd3=rdd1.cogroup(rdd2)
    rdd3.foreach(println)
    var rdd4 = rdd1.cogroup(rdd2,3)
    println(rdd4.partitions.size)

    var rdd5 = sc.makeRDD(Array(("A","A"),("E","E")),2)
    var rdd6 =rdd1.cogroup(rdd2,rdd5).foreach(println)

    // join
    var rdd7 = rdd1.join(rdd2).foreach(println)
    rdd1.preferredLocations(rdd1.partitions(0)).foreach(println)


  }



  def main(args: Array[String]): Unit = {
    testCogroup()
  }


}

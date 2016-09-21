package sparkBasic
import org.apache.spark.sql
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random

/**
  * Created by wjf on 2016/7/10.di
  */
object WordCount {
  val conf = new SparkConf().setAppName("wordCount")
  conf.setMaster("local")
  //  conf.setJars(List("D:\\development\\scala\\workspace\\spark-examples\\out\\artifacts\\WordCount\\WordCount.jar"))
  conf.set("spark.cores.max", "6")
  conf.set("spark.executor.memory", "3G")
//  conf.set("fs:default.name","file://")
  val sc = new SparkContext(conf)

  def main(args: Array[String]) {
    //   if( args.length <1){
    //     println("usage:<file>")
    //     System.exit(1)
    //   }
    //    println("wjf")
    //    println(args(0))
    //    val conf=new SparkConf().setAppName("Wordcount")
    ////    conf.setJars("D:\\development\\scala\\workspace\\spark-examples\\out\\artifacts\\WordCount\\WordCount.jar")
    ////    conf.setMaster("spark://master:7077").setJars(List("))
    //    conf.set("spark.cores.max","4")
    //    conf.set("spark.executor.memory","2G")
    //    val sc=new SparkContext(conf)
    ////    sc.addJar("D:\\development\\scala\\workspace\\spark-examples\\out\\artifacts\\WordCount\\WordCount.jar")
    //    val line=sc.textFile(args(0))
    //
    //    line.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).collect().foreach(println)
    //
    //    sc.stop()
//    var path = "hdfs://master:9000/hadoop/example-input.txt"
//    wordCount(path)
//    calculatePi()
//    var array:Array[Any]=new Array[Any](0)
//      groupByTest(new Array[Any](0))
//    var path="d://data//123.txt"
//    path="hdfs://133.133.135.83:9000/tmp/test.txt";
//    print(path)
    wordCount(args(args.length-1))
  }

  def wordCount(path: String): Unit = {
    val textFile = sc.textFile(path)
    textFile.foreach(println)
    val counts = textFile.flatMap(line => line.split(" ")).map(
      word => (word, 1)).reduceByKey(_ + _).collect()
    println(counts)
    counts.foreach(println)

//    counts.saveAsTextFile("hdfs://master:9000/hadoop/spark_wordcount3.txt")

  }
  def calculatePi(): Unit ={
    val NUM_SAMPLES=1000
    val count=sc.parallelize(1 to NUM_SAMPLES).map{i=>
    val x=Math.random()
    val y=Math.random()
    if(x*x + y*y <1 ) 1 else 0}.reduce(_+_)
    println("pi is roughly" + 4.0*count/ NUM_SAMPLES)

  }
  def textSearch(): Unit ={
    val textFile=sc.textFile("d://")
//    val df=textFile.

  }
  def hdfsTest(): Unit ={
//    val spark=SparkSession.
  // SparkSession is contains in spark 2.0 to repplace SqlContext and HiveContext
//    sc=SQLContext
    val rdd=sc.textFile("").toJavaRDD()
//    val mapped=rdd.map(s => s.toString.length).cache

  }
  def groupByTest(args:Array[Any]): Unit ={
    val numMappers=if(args.length>0) args(0).toString.toInt else 2
    val numKVPairs=if(args.length>1) args(1).toString.toInt else 1000
    val valSize = if(args.length >2 ) args(2).toString.toInt else 1000
    val numReducers = if(args.length>3)args(3).toString.toInt else numMappers
    val pairs=sc.parallelize(0 to numMappers).map{
      p =>
        val ranGen=new Random
        val arr1=new Array[(Int,Array[Byte])](numKVPairs+1)
        for(i <- 0 until numKVPairs){
          val byteArr=new Array[Byte](valSize+1)
          ranGen.nextBytes(byteArr)
          arr1(i)= (ranGen.nextInt(Int.MaxValue),byteArr)
        }
        arr1
    }.cache()
    println(pairs.count())
    pairs.groupBy(println)
    pairs.foreach(println)
//    sc.stop()
  }


}

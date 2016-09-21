//package otcaix
//
//import org.apache.spark.sql.SparkSession
//
///**
//  * Created by wjf on 2016/7/19.
//  */
//object PageRank {
//  val spark=(new MyConf)spark
//  def showWarning(): Unit ={
//    """
//      |Warn:this is a native implementation of PageRank
//      |and  is given as an example
//    """.stripMargin
//  }
//
//  def main(args: Array[String]) {
//    val iters = 10
//    val lines=spark.read.text("d://123.txt").rdd
//    val links=lines.map{ s=> val parts=s.split(" ")
//      (parts(0),parts(1))}.distinct().groupByKey().cache()
//    var ranks =links.mapValues(v => 1.0)
//    for(i <- 1 until iters){
//      val contribs=links.join(ranks).values.flatMap{
//        case(urls,rank) =>
//          val size=urls.size
//          urls.map(url => (url,rank/size))
//      }
//      ranks=contribs.reduceByKey(_+_).mapValues(0.15+0.85*_)
//
//    }
//    val output=ranks.collect()
//    output.foreach(tup => println(tup._1 + "has rank:"+ tup._2+"."))
//    spark.stop()
//  }
//}

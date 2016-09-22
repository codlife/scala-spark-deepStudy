package sparkBasic

import breeze.linalg.DenseVector
import breeze.linalg.{squaredDistance,Vector}
/**
  * Created by wjf on 2016/7/19.
  */
object KMeans {
  val spark=(new MyConf)spark
  def parseVector(line:String): Vector[Double]={
    DenseVector(line.split(" ")).mapValues(_.toDouble)
  }
  def closesToPoint(p:Vector[Double], centers:Array[Vector[Double]]):Int={
    var bestIndex =0
    var closest=Double.PositiveInfinity
    for(i <- 0 until centers.length){
      val tempDist= squaredDistance(p,centers(i))
      if(tempDist < closest){
        closest=tempDist
        bestIndex=i
      }
    }
    bestIndex
  }

  def main(args: Array[String]) {

    val lines=spark.read.textFile("d://123.txt").rdd
    val data=lines.map(parseVector _).cache()
    val k=2
    val convergeDist =0.1
    val kPoints= {data.takeSample(withReplacement = false,k,42)}
    var tempDist=1.0
    while(tempDist > convergeDist){
      val closest =data.map(p => (closesToPoint(p,kPoints),(p,1)))
      closest.foreach(println)
      val pointStats=closest.reduceByKey{case ((p1,c1),(p2,c2))  => (p1+p2,c1+c2) }
      pointStats.foreach(println)
      val newPoints =pointStats.map{pair => (pair._1,pair._2._1 * (1.0/ pair._2._2))}.collectAsMap()
      newPoints.foreach(println)
      tempDist=0.0
      for(i <- 0 until k){
        tempDist +=squaredDistance(kPoints(i),newPoints(i))
      }
      for(newP <- newPoints){
        kPoints(newP._1)=newP._2
      }
      println("finished iteration (delta= "+tempDist+")")
    }
    println("finish centers:")
    kPoints.foreach(println)
    spark.stop()

  }

}

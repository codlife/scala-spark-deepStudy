package sparkBasic

import scala.util.Random
import scala.collection.mutable
/**
  * Created by wjf on 2016/7/19.
  */
object TransitiveClosure {
  val spark=(new MyConf)spark
  val numEdges=200
  val numVertices=100
  val rand=new Random(42)
  def generateGraph:Seq[(Int,Int)]={
    val edges:mutable.Set[(Int,Int)]= mutable.Set.empty
    while(edges.size < numEdges){
      val from =rand.nextInt(numVertices)
      val to =rand.nextInt(numVertices)
      if(from!= to) edges.+=((from,to))
    }
    edges.toSeq

  }

  def main(args: Array[String]) {
    val slices = 2
    var tc = spark.sparkContext.parallelize(generateGraph, slices).cache()
    val edges = tc.map(x => (x._2, x._1))
    var oldCount = 0L
    var nextCount = tc.count()
    do {
      oldCount = nextCount
      // Perform the join ,obtaining an RDD of (y,(z,x)) pairs
      // then project the result to obtain the new (x,z) paths
      tc = tc.union(tc.join(edges).map(x => (x._2._2, x._2._1))).distinct().cache()
      tc.foreach(println)
      nextCount = tc.count()
    } while (nextCount != oldCount)

    println("TC has " + tc.count()+" edges.")
    spark.stop()
  }
}

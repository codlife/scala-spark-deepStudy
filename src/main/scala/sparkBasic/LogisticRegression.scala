package sparkBasic

import java.util.Random

import breeze.linalg.{DenseVector, Vector}
import breeze.numerics.exp
//import breeze.linalg.Vector

/**
  * Created by wjf on 2016/7/19.
  */
object LogisticRegression {
  val spark=(new MyConf)spark
  val N=10000
  val D=10
  val R=0.7
  val ITERATIOND=5
  val rand=new Random(42)
  case class DataPoint(x:Vector[Double],y:Double)
  def generateData:Array[DataPoint]={
    def generatePoint(i:Int): DataPoint={
      val y= if( i% 2==0) -1 else 1
      val x= DenseVector.fill(D){rand.nextGaussian+y*R}
      DataPoint(x,y)
    }
    Array.tabulate(N)(generatePoint)
  }

  def main(args: Array[String]) {
    val numSlice=2
    val points=spark.sparkContext.parallelize(generateData,numSlice).cache()
    var w=DenseVector.fill(D){2*rand.nextDouble() - 1}
    println("Initial w: "+w)
    for(i <- 1 to ITERATIOND){
      println("On iteration "+i)
      val gradient =points.map{p=>
      p.x* (1/ (1+ exp(-p.y* (w.dot(p.x))))-1)*p.y}.reduce(_+_)
      w-= gradient
    }
    println("Final w: "+ w)
    spark.stop()
  }

}

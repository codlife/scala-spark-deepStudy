package sparkBasic

import org.apache.hadoop.conf.Configuration
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by wjf on 2016/7/11.
  */
class MyConf {
  val conf=new SparkConf().setAppName("learn spark")
  conf.setMaster("local")
  val sc=new SparkContext(conf)
  val spark=SparkSession.builder.appName("example").getOrCreate()
//  var  arryay=new ArrayBuffer[]()

}

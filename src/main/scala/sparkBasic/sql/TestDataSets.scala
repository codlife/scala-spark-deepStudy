package sparkBasic.sql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import sparkBasic.MyConf
/**
  * Created by wjf on 2016/7/20.
  */
object TestDataSets {
  val sc=(new MyConf)sc
  val sqlContext=new SQLContext(sc)
  import sqlContext.implicits._
  def createDataSet(): Unit ={
    // encoders for most common types are automatically provided by
//     import sqlContext.implicits._
    val ds=Seq(1, 2, 3).toDS()
    ds.map(_+ 1).collect()
//    case class Person(name:String,age:Long)
    // encoder only support for primitive types(Int,String ,etc)
//    val dss=Seq(Person("Andy",32)).toDS()
    println(ds.toString())
  }
  case class Person(name: String,age:Int)
  def createDataFrame(): Unit ={
//    import sqlContext.implicits._

    val people = sc.textFile("d://123.txt").map(_.split(" ")).map( p =>
    Person(p(0).toString, p(1).trim.toInt)).toDF()
    people.registerTempTable("people")
    println(people.toString)
    val teenagers=sqlContext.sql("select name,age from people WHERE age>20")

    teenagers.foreach(p => println(p(0),p(1)))
    teenagers.map(t => "age"+t(1)).collect().foreach(println)
//    teenagers.map(t => "name:" + t(0)).collect().foreach(println)
//    teenagers.map(t => "name:" + t.getAs("name")).collect().foreach(println)
//    teenagers.map(t => t.getValuesMap[Any](List("name","age"))).collect().foreach(println)
//    teenagers.map(_.getValuesMap[String](List("name","age"))).collect().foreach(println)

  }
  // programmmatically specifying the schema
  def testSchema(): Unit ={
    val people =sc.textFile("d://123.txt")
    val schemaString= "name age"
    val schema= StructType(schemaString.split(" ").map(fieldName => StructField(fieldName,StringType,true)))

    val rowRDD=people.map(_.split(" ")).map(p => Row(p(0),p(1).trim))
    val peopleDataFrame =sqlContext.createDataFrame(rowRDD,schema)
    peopleDataFrame.registerTempTable("people")
    val results=sqlContext.sql("select name from people")
    results.map(t => "name： "+t(0)).collect().foreach(println)


  }
  def testParquetFile(): Unit ={
    val people =sc.textFile("d://123.txt").map(_.split(" ")).map(
      p =>Person(p(0).toString,p(1).trim.toInt)).toDS()
    people.write.parquet("d://people.parquet")
    val parquetFile =sqlContext.read.parquet("d://people.parquet")
    parquetFile.registerTempTable("parquetFile")
    val teenagers=sqlContext.sql("select name from parquetFile")
    teenagers.map( t => "name: "+ t(0)).collect().foreach(println)


  }
  def schemaMerging(): Unit ={
//    val df=sc.makeRDD(1 to 5).map( i => (i, i*2)).toDF("single","double")
//    df.write.parquet("d://data//test_table//key=1")
//    val df2=sc.makeRDD(6 to 10).map(i => (i,i* 3)).toDF("single","triple")
//    df2.write.parquet("d://data//test_table//key=2")

    val df3=sqlContext.read.option("mergeSchema","true").parquet("d://data//test_table")
    df3.foreach(p =>println(p))
    df3.printSchema()

  }
  def testJson(): Unit ={
    val people=sqlContext.read.json("d://data//data.json")
    people.printSchema()
    people.registerTempTable("people1")
    val teenagers= sqlContext.sql("select * from people1 ")
    teenagers.foreach(p =>println(p(0),p(1)))
    // load from json string directly


  }

  def main(args: Array[String]) {
//    createDataSet()
//    createDataFrame()
//    testSchema()
//    testParquetFile()
//    schemaMerging()

    testJson()
  }




}


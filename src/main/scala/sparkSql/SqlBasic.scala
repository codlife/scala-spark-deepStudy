package sparkSql

import org.apache.spark.sql.{Row, SparkSession}
//import org.apache.spark.sql.Encoder
/**
  * Created by wjf on 16-10-15.
  */
object SqlBasic {

  val sparkSession = SparkSession.builder().appName("spark sql basic").master("local").getOrCreate()

  // this import is needed to use the $-notation to convert a string to a column
  import sparkSession.implicits._
  def main(args: Array[String]): Unit = {

    val df = sparkSession.read.json("data/people.json")
    df.show()
    df.printSchema()
    df.select("people").printSchema()

    df.select($"name",$"age" + 1)show()

    df.filter($"age" > 21).show()
    df.groupBy("age").count().show()


    /**
      * besides this ,we can register a temp table to execute sql query
      */

    df.createOrReplaceTempView("people")

    val sqlDF = sparkSession.sql("SELECT * FROM people")

    sqlDF.show()


    /**
      * with encoder we needn't do deserialization before doing some operations like sorting,filtering,hashing
      */
    import sparkSession.implicits._


    val caseClassDS = Seq(Person("Andy", 32)).toDS()

    caseClassDS.show()

    // encoders for most common types are automatically provided by importing spark.implicits._

    val primitivesDS = Seq(1, 2, 3).toDS()
    primitivesDS.map(_ + 1).collect().foreach(println)

    val peopleDS = sparkSession.read.json("data/people.json").as[Person]
    println("this is people.json")
    peopleDS.show()


    /**
      * Inferring the Schema using Reflection
      */

    val peopleDF = sparkSession.sparkContext.textFile("data/people.txt").map(_.split(",")).map(attributes => Person(attributes(0), attributes(1).trim().toInt)).toDF()

    peopleDF.createOrReplaceTempView("people")
    val teenagersDF = sparkSession.sql("SELECT name, age FROM people WHERE age BETWEEN 13 AND 19")

    println("this is teenager")
    teenagersDF.map(teenager => "Name: " + teenager(0)).show()

    teenagersDF.map(teenager => "Name: " + teenager.getAs[String]("name")).show()


    implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[Map[String,Any]]
    teenagersDF.map(teenager => teenager.getValuesMap[Any](List("name", "age"))).collect().foreach(println)

    /**
      * Programmatically Specifying the Schema
      */

    import org.apache.spark.sql.types._
//
    val peopleRDD = sparkSession.sparkContext.textFile("data/people.txt")

    val schemaString = "name age"

    val fields = schemaString.split(" ").map(filedName => StructField(filedName, StringType, nullable = true))

    val schema = StructType(fields)

    val rowRDD = peopleRDD.map(_.split(",")).map(attributes => Row(attributes(0), attributes(1).trim))

    val pDF = sparkSession.createDataFrame(rowRDD, schema)

    pDF.createOrReplaceTempView("people")

    val results = sparkSession.sql("SELECT name FROM people")

    results.map(attributes => "Name: " + attributes(0)).show()




  }


}


// note: case class in scala 2.10 can support only up to 22 fields, to work around this limit,
// you can use custom classed that implement the Product trait
case class Person(name: String, age: Long)


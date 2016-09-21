package sparkBasic.sql

import org.apache.spark.sql.SQLContext
import sparkBasic.MyConf

/**
  * Created by wjf on 2016/7/20.
  */
object TestJson {
  val sc=(new MyConf)sc
  val sqlContext=new SQLContext(sc)
  val df=sqlContext.read.json("E://Json.json")
  def main(args: Array[String]) {
    df.show()
    df.printSchema()
//    df.select("data").show()
//    df.select(df("name"),df("age")+1).show()
    df.select(df("age")> 21).show()
  }
}

package sparkMllib

import org.apache.spark.sql.SparkSession

/**
  * Created by wjf on 2016/8/15.
  */
object MLLibConf {
    val spark= SparkSession.builder().master("local").appName("learn_mllib").config("spark.sql.warehouse.dir","file:///").getOrCreate()
}

package sparkMllib

import org.apache.spark.ml.feature.Word2Vec

/**
  * Created by wjf on 2016/8/15.
  */
object TestWord2Vector{
  val spark =MLLibConf.spark
  def main(args: Array[String]): Unit = {
    val documentDF =spark.createDataFrame(Seq(
      "Hi I heard about Spark".split(" "),
      "Hi I heard about Spark".split(" "),
      "Hi I heard about Spark".split(" ")
    ).map(Tuple1.apply)).toDF("text")
    documentDF.take(10).foreach(println)
    val word2Vec =new Word2Vec().setInputCol("text").setOutputCol("result").setVectorSize(3).setMinCount(0)


    val model =word2Vec.fit(documentDF)
    val result = model.transform(documentDF)
    result.select("result").take(5).foreach(println)

  }

}

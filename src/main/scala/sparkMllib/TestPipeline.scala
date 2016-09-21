package sparkMllib

//import org.apache.calcite.sql.advise.SqlSimpleParser.Tokenizer
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.{HashingTF, Tokenizer}
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.sql.{Row, SparkSession}

import org.apache.spark.ml.linalg.Vector
/**
  * Created by wjf on 2016/8/11.
  */
object TestPipeline {
  var spark=SparkSession.builder().master("local").config("spark.sql.warehouse.dir","file:///d://data")
    .appName("testPipeline").getOrCreate()

  def main(args: Array[String]) {

    val training = spark.createDataFrame( Seq(
      (0L,"a b c d e spark",1.0),
      (1L,"b d",0.0),
      (2L,"spark f g h",1.0),
      (3L,"hadoop mapreduce",0.0)
    )).toDF("id","text","label")
    val tokenizer = new Tokenizer().setInputCol("text")
      .setOutputCol("words")
    val hashingTF= new HashingTF().setNumFeatures(1000).setInputCol(tokenizer.getOutputCol).setOutputCol("features")
    val lr =new LogisticRegression().setMaxIter(10).setRegParam(0.01)
    val pipeline = new Pipeline().setStages(Array(tokenizer,hashingTF,lr))

    val model =pipeline.fit(training)
    model.write.overwrite().save("/tmp/unfit-lr-model")
    val sameModel =PipelineModel.load("/tmp/unfit-lr-model")
    val test=spark.createDataFrame(Seq(
      (4L,"spark i j k"),
      (5L,"l m n"),
      (6L,"mapreduce spark"),
      (7L,"apache hadoop")
    )).toDF("id","text")

    model.transform(test).select("id","text","probability","prediction").collect()
      .foreach({case Row(id:Long ,text:String,prob:Vector,prediction:Double) =>
      println(s"($id,$text) --> prob = $prob,prediction=$prediction")})
  }

}

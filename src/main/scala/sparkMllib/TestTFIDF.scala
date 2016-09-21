package sparkMllib

import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}

//import org.apache.calcite.sql.advise.SqlSimpleParser.Tokenizer

/**
  * Created by wjf on 2016/8/15.
  */
object TestTFIDF {
  def main(args: Array[String]): Unit = {
    val spark=MLLibConf.spark
    val sentenceData =spark.createDataFrame(Seq(
      (0,"Hi I heard about Spark"),
      (0,"I wish Java could use case classes"),
      (1,"Logistic regression models are neat")
    )).toDF("label","sentence")
    val tokenizer =new Tokenizer().setInputCol("sentence").setOutputCol("words")
    val wordsData= tokenizer.transform(sentenceData)
    wordsData.take(5).foreach(println)

    val hashingTF=new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(20)
    val featurizedData =hashingTF.transform(wordsData)
    //另外也可以选择CountVectorizer ：也可以获得 频率Vector

    featurizedData.take(5).foreach(println)


    val idf=new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel =idf.fit(featurizedData)
    val rescaledData =idfModel.transform(featurizedData)
    rescaledData.select("features","label").take(3).foreach(println)
  }




}


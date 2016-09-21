package sparkMllib

//import breeze.linalg.max
import breeze.linalg.max
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.{BinaryLogisticRegressionSummary, DecisionTreeClassificationModel, DecisionTreeClassifier, LogisticRegression}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
import org.apache.spark.sql.DataFrame

/**
  * Created by wjf on 2016/8/17.
  */
object TestClassificationAndRegression {
  val spark =MLLibConf.spark
  def main(args: Array[String]): Unit = {
//    testLogisticRegression()

    testDecisionTree()

  }
  def testLogisticRegression(): Unit ={
    val training = spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")

    val lr =new LogisticRegression().setMaxIter(10).setRegParam(0.3).setElasticNetParam(0.8)

    val lrModel =lr.fit(training)

    println(s"Coefficients: ${lrModel.coefficients} intercept:${lrModel.intercept}")

    val trainingSummary  = lrModel.summary

    val objectiveHistory = trainingSummary.objectiveHistory
    objectiveHistory.foreach(loss => println(loss))

    val binarySummary =trainingSummary.asInstanceOf[BinaryLogisticRegressionSummary]

    val roc =binarySummary.roc

    roc.show()
    println(binarySummary.areaUnderROC)

    val fMeasure:DataFrame  = binarySummary.fMeasureByThreshold
    val maxFMeasure =fMeasure.select("F-Measures").head().getDouble(0)

    val bestThreshold = fMeasure.where("F-Measures").select("threshold").head().getDouble(0)
    lrModel.setThreshold(bestThreshold)

  }

  def testDecisionTree(): Unit ={
    val data = spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")

    val labelIndexer =new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(data)

    val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(data)

    val Array(trainingData,testData) =data.randomSplit(Array(0.7,0.3))

    val dt= new DecisionTreeClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")

    val labelConverter =new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labels)

    val pipeline = new Pipeline().setStages(Array(labelIndexer,featureIndexer,dt,labelConverter))

    val model = pipeline.fit(trainingData)

    val predictions =model.transform(testData)

    predictions.select("predictedLabel","label","features").show()

    val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")

    val accuracy = evaluator.evaluate(predictions)

    println("Test Error = " + (1.0 -accuracy))

    val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]
    println("Learned classification tree model:\n" + treeModel.toDebugString)



  }


}

package sparkMllib

import org.apache.spark.ml.clustering.{BisectingKMeans, KMeans}
//import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.sql.SparkSession

/**
  * Created by wjf on 2016/8/11.
  */
object TestKmeans {
      var spark= SparkSession.builder().config("spark.sql.warehouse.dir","" +
        "file:///D:").master("local[2]")
        .appName("test_kmeans").getOrCreate()

      def main(args: Array[String]): Unit = {

        testBisectingKMeans()
      }
      def testKmeans(): Unit ={
        val dataset=spark.read.format("libsvm").load("data//mllib//sample_kmeans_data.txt")
        val kmeans =new KMeans().setK(2).setSeed(1L)
        val model =kmeans.fit(dataset)
        val WSSSE= model.computeCost(dataset)

        println("cluster centers:"+s" computting cost is: $WSSSE")
        model.clusterCenters.foreach(println)
      }
      def testBisectingKMeans(): Unit ={
        val dataset = spark.read.format("libsvm").load("data/mllib/sample_kmeans_data.txt")
    val bkm = new BisectingKMeans().setK(2).setSeed(1)

    val model = bkm.fit(dataset)

    val cost = model.computeCost(dataset)

    println(s"Within Set Sum of Squared Errors = $cost")

    println("Cluster Centers: ")
    val centers = model.clusterCenters

    centers.foreach(println)


  }

}

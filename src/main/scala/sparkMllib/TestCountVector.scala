package sparkMllib

//import breeze.linalg.PCA
import breeze.linalg.sum
import org.apache.spark.ml.feature._
import org.apache.spark.ml.linalg.{SparseVector, Vectors}

/**
  * Created by wjf on 2016/8/15.
  */
object TestCountVector {

  val spark=MLLibConf.spark
  def main(args: Array[String]): Unit = {
  /*  val df= spark.createDataFrame(Seq(
      (0,Array("a","b","C","c")),
      (1,Array("a","a","b","b","c","C"))
    )).toDF("id","words")

    val cvModel:CountVectorizerModel =new CountVectorizer().setInputCol("words").setOutputCol("features").setVocabSize(3).setMinDF(2).fit(df)

    val cvm =new CountVectorizerModel(Array("a","b","c")).setInputCol("words").setOutputCol("features")
    cvModel.transform(df).select("features","words").show(false)*/

//    removeStopWord()
//    nGram()
//    testPCA()
//    testPolynomialExpansion()

//    testDiscreteCosineTransform()

//    testStringIndexer()
//    testOneHotCoder()

//      testNormalizer()
//    testStandardScaler()

//    testMinMaxScaler()

    testVectorIndexer()
  }



  def removeStopWord(): Unit ={
    val remover =new StopWordsRemover().setInputCol("raw").setOutputCol("filtered")
    val dataSet =spark.createDataFrame(Seq(
      (0,Seq("I","saw","the","red","baloon")),
      (1,Seq("Marry","had","a","little","lamb"))
    )).toDF("id","raw")
    remover.transform(dataSet).show()
  }

  def nGram(): Unit ={
    val wordDataFrame =spark.createDataFrame(Seq(
      (0,Array("Hi","I","heard","about","Spark")),
      (1,Array("I","wish","Java","Could","use","case","classes")),
      (2,Array("Logistic","regression","models","are","neat"))
    )).toDF("label","words")

    val ngram =new NGram().setInputCol("words").setOutputCol("ngrams")
    val ngramDataFrame =ngram.setN(10).transform(wordDataFrame)
    ngramDataFrame.take(3).map(_.getAs[Stream[String]]("ngrams").toList).foreach(println)

  }
  def testPCA(): Unit ={
    val data =Array(
      Vectors.sparse(5,Seq((1,1.0),(3,7.0))),
      Vectors.dense(2.0,0.0,3.0,4.0,5.0),
      Vectors.dense(4.0,0.0,0.0,6.0,7.0)
    )
    val df =spark.createDataFrame(data.map(Tuple1.apply)).toDF("label","features")
    df.take(10).foreach(println)
    val pca=new PCA().setInputCol("features").setOutputCol("pcaFeatures").setK(1).fit(df)
    val pcaDF =pca.transform(df)
    pcaDF.take(10).foreach(println)
    val result = pcaDF.select("pcaFeatures","label")
    result.show()
  }
  def testPolynomialExpansion(): Unit ={
    val data= Array(
      Vectors.dense(-2.0,2.3),
      Vectors.dense(0.0,0.0),
      Vectors.dense(0.6,-1.1)
    )
    val df =spark.createDataFrame(data.map(Tuple1.apply)).toDF("features")
    val polynomialExpansion =new PolynomialExpansion().setInputCol("features").setOutputCol("polyFeatures").setDegree(3)
    val polyDF = polynomialExpansion.transform(df)
    polyDF.select("polyFeatures").take(3).foreach(println)
  }

  def testDiscreteCosineTransform(): Unit ={
    val data =Seq(
      Vectors.dense(0.0,1.0,-2.0,3.0),
      Vectors.dense(-1.0,2.0,4.0,-7.0),
      Vectors.dense(14.0,-2.0,-5.0,1.0)
    )
    data.foreach(println)

    val df=spark.createDataFrame(data.map(Tuple1.apply)).toDF("features")
    // take some cow to the driver program. if the n is too large ,the driver program may be crash
    df.take(10).foreach(println)
    val dct =new DCT().setInputCol("features").setOutputCol("featuresDCT").setInverse(false)

    val dctDF = dct.transform(df)

    dctDF.select("featuresDCT").show(3)

  }


  def testStringIndexer(): Unit ={
    val df =spark.createDataFrame(Seq(
      (0,"a"),(1,"b"),(2,"c"),(3,"a"),(4,"a"),(5,"c")
    )).toDF("id","category")

    df.take(6).foreach(println)
    val indexer =new StringIndexer().setInputCol("category").setOutputCol("categoryIndex").fit(df)

    val indexed = indexer.transform(df)
    indexed.take(6).foreach(println)

    val converter =new IndexToString().setInputCol("categoryIndex").setOutputCol("originalCategory")

    val converted = converter.transform(indexed)
    converted.select("id","categoryIndex","originalCategory").show()

  }
  def testOneHotCoder(): Unit ={
    val df = spark.createDataFrame(Seq(
      (0,"a"),(1,"b"),(2,"c"),(3,"a"),(4,"a"),(5,"c"),(6,"b")
    )).toDF("id","category")
    val indexer = new StringIndexer().setInputCol("category").setOutputCol("categoryIndex").fit(df)

    val indexed = indexer.transform(df)
    val encoder =new OneHotEncoder().setInputCol("categoryIndex").setOutputCol("categoryVec")

    val encoded = encoder.transform(indexed)
    encoded.select("id","categoryVec").show()

  }

  def testNormalizer(): Unit ={
    val df =spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")
    val normalizer = new Normalizer().setInputCol("features").setOutputCol("normFeatures").setP(2.0)
    df.show()
    val l1NormData = normalizer.transform(df)
    l1NormData.show()
    val lInfNormData = normalizer.transform(df,normalizer.p -> 2)

//    val array = lInfNormData.take(10).map(r => r.getAs[SparseVector](1).values)
    val  a=lInfNormData.take(10).map(r => r.getAs[SparseVector](1).values)
    a.foreach(array =>println(array.toList))
//    array.foreach(array => println(array.toList))

  }

  def testStandardScaler(): Unit = {
    val df = spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")

    val scaler = new StandardScaler().setInputCol("features").setOutputCol("scaledFeature").setWithStd(true).setWithMean(false)

    val scalerModel = scaler.fit(df)
    val scaledData = scalerModel.transform(df).take(10).map( r => r.getAs[SparseVector](1).values)
//    val means =scaledData.means
    val a=Array(1.0,2,3)
    val means=a.sum/ a.size
    val std= a.map(n => (n - means) *( n - means) ).reduce( _ + _)
    println(math.sqrt(std))

    scaledData.foreach( array => println(array.toList))
    scaledData.foreach { row =>
      val means = row.sum
      val std=row.map( elem => (elem-means)*(elem- means)).reduce(_ + _)
      println((std))
    }
  }

//    val scaled

  def testMinMaxScaler(): Unit ={
    val df= spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")
    val scaler =new MinMaxScaler().setInputCol("features").setOutputCol("scaledFeatures")

    val scalerModel =scaler.fit(df)

    val scaledData = scalerModel.transform(df)
    scaledData.take(5).foreach(println)

  }
//  det tes
  def  testVectorIndexer(): Unit ={

//    val data= spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")
    val myData=spark.read.format("libsvm").load("d://data/123.txt")
//    data.take(10).foreach(println)
    myData.take(10).foreach(println)
    val indexer =new VectorIndexer().setInputCol("features").setOutputCol("indexed").setMaxCategories(2)

    val indexerModel = indexer.fit(myData)

    val categoricalFeatures :Set[Int]= indexerModel.categoryMaps.keys.toSet
    println(s"Chose ${categoricalFeatures.size} categorical features: "+ categoricalFeatures.mkString(","))

//    val indexedData = indexerModel.transform(data)
//    indexedData.select("indexed").take(10).foreach(println)
//    indexedData.take(10).foreach(println)

    val myIndexedData =indexerModel.transform(myData)
    myIndexedData.take(10).foreach(println)


  }

}

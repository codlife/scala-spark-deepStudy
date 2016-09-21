package sparkBasic

//import org.apache.spark.rdd.RDD
import org.datanucleus.enhancer.jdo.JDOClassEnhancer.MyClassVisitor

//import otcaix.MyConf
/**
  * Created by wjf on 2016/7/11.
  */
object SparkBasic {
  val sc = (new MyConf).sc

  def main(args: Array[String]): Unit = {
    val textFile = sc.textFile("d:\\123.txt")
    //    textFile.foreach(println)
    //    println(textFile.count())
    //    println(textFile.first())
    val lineWithWJF = textFile.filter(line => line.contains("wjf"))
    println("------------")
    //    lineWithWJF.foreach(println)
    //    println(lineWithWJF.count())
    //    val num=textFile.map(line => line.split(" ").size).reduce((a,b) => Math.max(a,b))
    val wordCount = textFile.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey(
      (a, b) => a + b)
    println(wordCount)
    wordCount.collect().foreach(println)
//    val obj = new MyClass
    //    val data=textFile.map(obj.doStuff())
//    val rdd=wordCount.map(x => x+"1")
//    val rdd2=MyClass.doStuff(textFile)
//    val rdd3=textFile.map(MyClass.funcOne)
//    val obj=new My1()
//    val rdd4=obj.doStuff(textFile)
////    val rdd4=textFile.map((new My1()).doStuff())
//
//    val obj2=new My2
//    val rdd5=obj2.doStuff(textFile)
    val paris=textFile.map(S=>(S,1))
    val counts=paris.reduceByKey((a,b) => a+b)
    println(counts)
    val s=11
    var ss="ss"

  }


}


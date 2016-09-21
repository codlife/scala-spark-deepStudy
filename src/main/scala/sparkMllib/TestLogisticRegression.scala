package sparkMllib

/**
  * Created by wjf on 2016/8/16.
  */
object TestLogisticRegression {
  def main(args: Array[String]): Unit = {
//      println(EPSILON)
    testZip()

  }
  private[sparkMllib] lazy val EPSILON: Unit ={
    var eps=1.0
    while( (1.0 + (eps /2.0 ))!= 1.0){
      eps/= 2.0
    }
    eps
  }
  def testZip(){
    val seq=Seq(1,1,2,2,3)
    val ziped=seq.zipWithIndex
    ziped.foreach(println)
    val value=ziped.unzip
    (value _2).foreach(println)
    val a=1
    require(a >2 ,"sorry a < 2")

  }
}

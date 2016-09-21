package sparkBasic

/**
  * Created by wjf on 2016/8/6.
  */
object TestRDD {
  val sc=(new MyConf).sc
  def main(args: Array[String]) {

  }
  def testMapWith(): Unit ={
    val x=sc.parallelize(List(1,2,3,4,5,6,7,8,9,10),3)
//    x.mapPartitionsWithIndex()

  }

}

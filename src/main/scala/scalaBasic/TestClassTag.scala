package scalaBasic

/**
  * Created by wjf on 2016/8/6.
  */
object TestClassTag {
  def main(args: Array[String]) {
    println(classOf[List[Int]] == classOf[List[String]])


  }

}

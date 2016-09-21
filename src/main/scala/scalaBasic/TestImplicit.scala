package scalaBasic

import java.io.File

import scala.io.Source

/**
  * Created by wjf on 2016/8/5.
  */
object TestImplicit {
  def display(input: String): Unit = println(input)

  implicit def typeConvertor(input: Int): String = input.toString

  implicit def typeConvretor2(input: Boolean): String = if (input) "true" else "false"

  // implicit def type(input:Boolean):String=if(input) "true" else "false"
  /*
  * implicit depends on only the input  type and the return type , have nothing to do with the function name */
  def main(args: Array[String]) {
    display("w")

    display(12)
    display(true)
    Param.print("wjf")("hello")
//    import Context._
//    import CC.file2RichFile
//    Param.print("csm")
//    import CC.file2RichFile
    import Test.Context.file2RichFile

    println(new File("d://data//123.txt").read)
  }
  /*
  * 为现有类库增加功能的一种方式，用java 的话，只能用工具类或继承的方式
  * 而scala可以使用隐士转换
  * */
//  object Context {
//    implicit val ccc: String = "implicit"
//  }

  object Param {
    def print(context: String)(implicit prefix: String): Unit = {
      println(prefix + ":" + context)
    }
  }
}



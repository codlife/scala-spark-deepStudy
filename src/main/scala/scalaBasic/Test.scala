package scalaBasic

import java.io.File



import scala.util.Random

//import breeze.numerics.sqrt

import math._
//import breeze.numerics.sqrt

import scala.io.Source

/**
  * Created by wjf on 2016/8/5.
  */
object Test {
  object Context{
    implicit def file2RichFile(f:File) =new RichFile(f)
  }

  def main(args: Array[String]) {
    println((new AA("wjf",1)).get())




    import Context.file2RichFile
    println(new File("d://data//123.txt").read)
    println(sqrt(2))
    println(BigInt.probablePrime(100,Random))
    println("Hello".distinct)
    println("hello"(3))
    println("Harry".patch(1,"ung",2))
    println("Harry".patch(1,"ung",1))
  }

}
class RichFile(val file:File) {
  def read =Source.fromFile(file.getPath).mkString
}


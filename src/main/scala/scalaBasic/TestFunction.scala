package scalaBasic

/**
  * Created by wjf on 2016/8/14.
  */

/*本章要点：
* .Scala 中函数是头等公民，就和数字一样
* .你可以创见匿名函数，通常还会把他们交给其它函数
* .函数参数可以给出需要稍后执行的行为
* .许多集合犯法都可以接受函数参数，经函数应用到集合中的值
* .有很多语法上的简写让你可以以简单且一度的方式表达函数参数
* .你可以创见代码操作块的函数，他们看上去就像是内建的控制语句
* */
object TestFunction {
  def main(args: Array[String]): Unit = {
    /*1：匿名函数*/
    var x= (x:Double) => 3*x
    Array(1,2).map((x:Int) => 2*x)


    /*2: 高阶函数：接受函数参数，或者返回函数*/
    def mulBy(factor:Double) =(x:Double) => factor *x
    // 函数参数类型是(double) => ((double) => double)
    val quintuple =mulBy(5)
    println(quintuple(20))

    /*2:类型推断*/
//    val fun =3*_  错误 ，无法推断出类型
    val fun =3* (_ :Double) //ok
    val fun2:(Double) => Double =3* _ //ok ,因为func2有了类型

    /*3：一些有用的高阶函数*/
    (1 to 9).map("*" * _).foreach(println _)
    // reduceLeft 方法接受一个二元的函数，寄一个带有两个参数的函数-并将它们应用到序列中的所有元素，从左到右，例如：
    (1 to 9 ) reduceLeft(_ * _) // 1*2*3*....*9 == (... ((1*2)*3...*9)
    "mary has a little lamb".split(" ").sortWith(_.length < _.length).foreach(println)

    /*4:闭包：闭包由代码和代码用到的任何非局部变量定义构成：这些函数实际上是一类的对象方式实现的，该类有一个实例变量factor
    * 和一个包含了函数体的apply 方法*/
    val triple =mulBy(3)
    val half =mulBy(0.5)
    println(triple(14)+" "+half(14))
    /*5:柯里化：将原来接受两个参数的函数变成新的接受一个参数的函数的过程，新的函数返回一个以原有第二个参数为参数的函数*/
    def nulOneAtATime(x:Int)(y:Int) =x *y
    /*6: return 如果return 语句被异常捕获了，那么return 值就不会被传到带名函数中了*/
    def indexOf(str:String):Int={
      try{
        throw new IllegalArgumentException
        return 1
      }
    }
  }

}

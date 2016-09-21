package scalaBasic

/**
  * Created by wjf on 2016/8/6.
  */
object TestLogicExpression {
  def main(args: Array[String]) {
    testIf()
    testAssignOpeartion()
    testInputOutput()
  }

  def testIf(): Unit = {
    /*不同于 java 或者 C++ ,Scala 中IF/ELSE 表达式有值
  * if else 表达式有类型，就是返回值的类型，或者是返回值的超类型
  * scala 没有switch */
    val x = 1
    val s = if (x > 0) 1 else -1
    //    if (x > 0) 1 等价于 if ( x>0) 1 else ()

  }
  def testAssignOpeartion(): Unit ={
    var x=1
    var y=x=1
    // 在 java 中上面是有意义的，但是在Scala 中没有意思
    // scala 中 赋值语句的值是Unit ，上面相当于是将y=Unit
    println(y)
  }
  def testInputOutput(): Unit ={
    print()
    println()
    printf("%s%d","wjf",2)
    val name= readLine("your name:")
    val age=readInt()
    printf("%s%d",name,age)
  }
  def testFor(): Unit ={
    // to
    // until
    for(c <- "hello"; i <- 0 to 1)yield (c+i).toChar
    for(i <- 1 to 3;from = 4-i;j <- from to 3) yield i*10+j

  }
  def testLazy: Unit ={
    // 变量被声明为lazy 他的初始化将被推迟，知道首次使用它时
    // lazy 并非没有额外开销，每次使用前，都会有一个方法去检查这个值是否已被初始化
    lazy val words= scala.io.Source.fromFile("xx")
  }
  // val s=sum( 1 to 4:_*)
  def testAlterableArgs(args:Int*): Unit ={
    var result=0
    for(arg <- args) result+=arg
    result
  }


}

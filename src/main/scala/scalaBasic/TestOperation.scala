package scalaBasic
import scala.collection.mutable.HashMap
/**
  * Created by wjf on 2016/8/14.
  */
/*本章我们学习Scala 操作符*/
object TestOperation {
  def main(args: Array[String]): Unit = {





    /*特别声明：注意下面的地方是不一样的
    * 《快学Scala》中说 b toString 等同于b.toString ,显然这个说法是错的，下面就是很好的例子*/
    var a=1
    var b=2
    println(a + b toString) // 3
    println(a + b.toString) //12


    val author="wjf hello"
    author match {
      case Name(first,last) => println(first+"-"+last)
      case Name(first,middle,last) =>println(first+"-"+middle+"-"+last)
      case _ => println("none")
    }
  }
  /*理论上任何Unicode 都可以成为合法的表示符，初次之外你也可是使用任意序列的操作符字符：
  * 比如 ！#+- 等等
  * 你可以在反引号中包含几乎任何字符序列，例如*/
  def test: Unit ={
    val `val` =42
  }
  // 上面的的实例很糟糕，但是反引号有时确实可以成为“逃生舱门“，比如yield 你可能要访问java 中一个同样命名的方法
  // 反引号来拯救你
  // Thread.`yield`()
  /*自定义操作符比如定义 * (n1/d1) *(n2/d2) =(n1*n2 /d1*d2)*/
  class Fraction(n:Int ,d:Int){
    private val num=n
    private val den=d
    def *(other:Fraction) =new Fraction(num * other.num,den*other.den)
  }
  /* apply和 update
  * 如果f 不是函数或方法，那么如下表达式就等同于调用
  * f(arg1,arg2...) f.apply(arg1,arg2,...)
  * f(arg1,arg2,...)=value f.update(arg1,arg3,...,value)*/
  val scores=new HashMap[String,Int]
  scores("bob")=100 // 调用 scores.update
  val bobsScore =scores("bob") // 调用scores.apply("bob")

  /*unapply 提取器，提取值*/
  case class CC(value:Double,unit:String)
//  var v=new CC(1,"RMB")
//  case CC(1,"RMB") => println("RMB"+1)
  /*unapplySeq 方法*/
  object Name{
    def unapplySeq(input :String):Option[Seq[String]] =
      if(input.trim== "") None else Some(input.trim.split("\\s+"))
  }

}



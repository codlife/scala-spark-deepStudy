package scalaBasic


/**
  * Created by wjf on 2016/8/5.
  */
object TestOption {
  /*
  * scala 的 Option[T] 是容器对于给定的类型的零个或一个元件
  * Option[T] 可以是一些[T] 或None 对象，*/
  def main(args: Array[String]) {
    val a:Option[Int] = Some(2)
    val b:Option[Int] = None
    println(a.getOrElse(0))
    println(b.getOrElse(1))
    println( a.isEmpty)
    println( b.isEmpty)
    show(None)
    val map=Map("name" -> "wjf","age" -> "s")
    val s:Option[String] =Some("S")
    show(s)
    println((new AA("wjf",1)).get())
    println()
  }
  def show(x:Option[String]) = x match{
    case Some(s) => s
    case None => "?"
  }
}


class BB(name:String,tage:Int ) extends AA(name:String,tage:Int) {

  var ss="ss"
  def this(s:String,tage:Int,home:String)={

    this(s,tage)


  }
   override def get(): String ={
    ss
  }
}
private class A1(s:String){
  def get(): String ={
    s
  }
}
class AA private  {
  var s="ss"
  var age=1
  def this(name:String,tage:Int){
    this()
    age=tage
  }
  def get(): String ={
    s
  }
}


package scalaBasic

/**
  * Created by wjf on 2016/8/7.
  */
/*
* zai java 中，你通常会即用到实例方法又用到静态方法的类。
* 在Scala 中，你可以通过类和与类同名的”伴生“对象来达到相同的目的
* 类 和 它的伴生对象可以相互访问私有的特性
* 他们必须在同一个源文件中*/
object TestObject {
  private var lastNumber=0
  private def newUniqueNumber()={
    lastNumber+=1
    lastNumber
  }
}
class TestObject{
  val id=TestObject.newUniqueNumber()
  private var balance= 0.0
  def deposit(amount:Double){balance += amount}
}
/*扩展类 或 trait 的对象
* 一个object 可以扩展类以及一个或多个trait
* 针对下面的类：
* val actions= Map("open" -> DoNothingAction,"save" -> DoNothingAction)*/
abstract class UndoableAction(val desc:String){
  def undo():Unit
  def redo():Unit
}
object DoNothingAction extends UndoableAction("do nothing"){
  override def undo(){}
  override def redo(){}
}
/*apply 方法
* 注意 Array(100) 和new Array(100)的区别
* 前者：应用了apply 方法，产生一个元素
* 后者定义了100个值为null 的元素
* 自定义apply 实例：
* val acct=Account(100) 使用apply
* val acct=new Account(100) 使用 构造方法*/
class Account private(val value:Double){
  private var v=value
}
object Account{
  def apply(value:Double): Unit ={
    new Account(value)
  }
}

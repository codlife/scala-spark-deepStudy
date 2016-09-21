package scalaBasic

/**
  * Created by wjf on 2016/8/4.
  */
object TestTrait {
  def main(args: Array[String]) {
    val fishEagle =new FishEagle
    val flyable:Flyable=fishEagle
    val swimmer:Swimable=fishEagle
    flyable.fly
    swimmer.swim
  }
}
/* 类似于interface ，可以翻译为特征， 所以使用 with ，而不是implements,可以实现部分函数，这点不同于java
trait 的存在主要是为了实现多继承，具有多个特征，
让特质拥有具体行为存在一个弊端，当特质改变时，所有混入了该特质的类必须重新编译

*/
trait Flyable{
  def hasFeather =true
  def fly
}
trait Swimable{
  def swim
}
abstract class Animal{
  def walk(speed:Int)
  def breathe()={
    println("animal breathers")
  }
}
class FishEagle extends Animal with Flyable with Swimable{
  override def walk(speed: Int) = println("" + "fih eagle walk with speed"+speed)


  override def fly: Unit =println("fish eagle fly fast")


  override def swim: Unit = println("fish eagle swim fast")
}

/*
* object with  trait*/
trait Logged{
  def log(msg:String){}
}
class SavingsAccount extends Logged{
  def withdraw(amount :Double): Unit ={
    if(amount > 10 ) log("insufficient funds")
    else {}
  }
}
// 构造对象的时候可以加入不同的特质
// val acct =new SavingsAccount with ConsoleLogger
// val acct2=new SavingsAccount with FileLogger

//特质 执行的顺序 由后向前
// val acct1= new SavingsAccount with TimestampLogger with ShortLogger
// val acct2= new SavingsAccount with ShortLogger with TimestampLogger
// 如果需要 控制具体是哪一个特质比被调用 ，可以使用 super[ShortLogger].log(...)

// 在特质中重写方法
trait Logger{
  def log(msg:String)
}
trait TimestampLogger extends Logger{
  abstract override def log(msg:String): Unit ={
    super.log(new java.util.Date()+msg)
  }
}
// 上面的方法调用了 super.log 但是 super.log并没有实现，所以需要加上abstract 关键字

// 特质与类的唯一技术区别是 缺少构造器参数，特质不能有构造器参数，每个特质都有呀一个无参数的构造器

/*t
*特质背后发生了什么
* Scala 需要将特质翻译成JVM 的类和接口。背后的原理是：
* 只有抽象方法的特质被翻译成了接口
* 如果特质中有具体的方法Scala 会帮我们创建出一个伴生类，该伴生类用静态方法存放特质的方法
* 这些伴生类不会有任何字段，特质中的字段对应到接口中的抽象的Getter 和 Setter 方法
*
* 如果特质实现某个超类，则伴生类不会继承该超类，该超类会被任何实现该特质的类继承*/
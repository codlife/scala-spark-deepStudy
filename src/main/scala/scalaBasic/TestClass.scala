package scalaBasic

/**
  * Created by wjf on 2016/8/7.
  */
object TestClass {

  def main(args: Array[String]) {
    var obj=new Person2
//    println(obj.age)
    var obj2=new Person
    println(obj2.age)
    obj2.age=21
    println(obj2.age)
  }
  /*Scala 生成面向对象的JVM类，期中有一个私有的age字段以及相应的
  * getter setter方法。这两个方法是公有的，因为我们没有吧age
  * 生命为 private
  * 对于私有字段，getter 和setter 也是私有的*/
  class Person{
    var age=0 //必须初始化
  }
  class Person2{
    private var age=0
  }
  /*scala 自动生成的get: age set:age_= 任何时候我们都可以自定义get/set
  * */
  class Person3{
    private var privateage=0
    def age=privateage
    def age_=(newValue:Int): Unit ={
      if(newValue > privateage) privateage=newValue
    }
  }
  /*
  * 如果字段是私有的，那么getter 和setter 也是私有的
  * 如果字段是val ，则只有getter方法被生成
  * 如果你不需要任何getter 和setter 可以生命字段为private[this]*/

  /*
  * 赋值构造器：
  * 1.辅助构造器的名称为this.
  * 2.每一个辅助构造器都必须以一个对先前已定义的其他辅助构造器或者是
  * 主构造器开始*/
  class Person4{
    private var name=""
    private var age=0
    def this(name:String){
      this() // 从调用主构造器开始
      this.name=name
    }
    def this(name:String,age:Int){
      this(name) // 从调用从构造器开始
      this.age=age
    }
    // var p1=new Person
    // var p2=new Person("wjf")
    // var p3=new Person("wjf",2)
  }
  /*
  * 主构造器：
  * 主构造器会执行类定义中的所有语句*/
  class Person5(val name:String,val age:Int){
    println("Just construct a person"+name+age)
  }
  /*自动执行类定义中的语句这个特性有时候特别有用：
  * 在构造过程中配置某个字段
  * 类似于Java 中的构造函数前面的代码块{}*/


}

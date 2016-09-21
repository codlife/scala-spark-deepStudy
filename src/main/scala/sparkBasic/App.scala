package sparkBasic

/**
 * Hello world!
 *
 */
object App  {
  println( "Hello World!" )
}
// ctrl +o  to override
class A{
  def a()=println("a")
}
class B extends A{
  override def a(): Unit = super.a()

}

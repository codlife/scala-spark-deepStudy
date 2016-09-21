package scalaBasic.society.professonal

/**
  * Created by wjf on 2016/8/5.
  */
class Executor {
  private[professonal] var word=null
  private[society] var friends=null
  private[this] var secrets=null
  def help(another:Executor): Unit ={
    println(another.word)
    println(another.friends)
    // private[this] 的访问权限是当前对象实例
//    println(another.secrets)
  }

}

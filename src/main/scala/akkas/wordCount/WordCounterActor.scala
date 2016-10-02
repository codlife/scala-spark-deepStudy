package akkas.wordCount

/**
  * Created by wjf on 16-10-2.
  */
case class StartProcessFileMsg()

/**
  * sender: 当前处理消息的发送者的一个ActorRef引用
  * context: Actor 运行上下文信息的信息和方法（包括实例化一个新的Actorde的方法ActorOf）
  * supervisionStrategy: 定义用来从错误中恢复的策略
  * self：Actor本身的Actor引用
  * AKKA 确保Actor 的每个实例都运行在自己的轻量级线程里, 并保证每次只处理一条消息，这样一来，开发者不必担心同步或者竞态
  * 的出现，而每个Actor的状态都可以被可靠地保持。
  *
  * 需要注意的是，在akka中，actor 之间唯一的通信方式就是消息传递，消息是actor之间唯一共享的东西，而且因为多个actor
  * 可能会并发访问同样的消息，所以为了避免竞争条件和不可预期的行为，消息的不可变性非常重要
  *
  * 因为 cass class 默认是不可变得并且可以和模式匹配无缝集成，所以用case class 的形式来传递消息是很常见的。
  * scala 中的case class 就是正常的类，唯一不同的是通过模式匹配提供了可以递归分解的机制。
  * @param fileName
  */
class WordCounterActor(fileName: String) extends Actor {
  private var running = false
  private var totalLines = 0
  private var linesProcessed = 0
  private var result =0
  private var fileSender: Option[ActorRef]  = None
  def receive = {
    case StartProcessFileMsg() => {
      if (running) {
       println("log")
      } else {
        running = true
        // save reference to process invoker
        fileSender = Some(sender)
        import scala.io.Source._
        fromFile(fileName).getLines().foreach {
          line =>
            context.actorOf(Props[StringCounterActor]) ! ProcessStringMsg(line)
            totalLines += 1
        }
      }
    }
    case StringProcessedMsg(words) => {
      result += words
      linesProcessed += 1
      if (linesProcessed == totalLines) {
        // provide result to process invoker
        fileSender.map(_ ! result)
      }
    }
  }
  case _ => println("message not recognized")


}

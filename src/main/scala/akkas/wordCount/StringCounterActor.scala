package akkas.wordCount

import scala.akka.


/**
  * Created by wjf on 16-10-2.
  */
case class ProcessStringMsg(string: String)
case class StringProcessedMsg(words: Integer)
class StringCounterActor extends Actor {
  def receive = {
    case ProcessStringMsg(string) => {
      val wordsInLine = string.split(" ").length

      sender ! StringProcessedMsg(wordsInLine)
    }
    case _ => println("Error: message not recognized")
  }


}

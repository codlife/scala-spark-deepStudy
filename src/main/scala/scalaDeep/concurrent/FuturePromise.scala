package scalaDeep.concurrent

import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by wjf on 16-9-22.
  */
case class TaxCut(reduction: Int)

object FuturePromise {

  def redeemChampignPledge(): Future[TaxCut] = {
    val p =Promise[TaxCut]()
    Future {
      println("starting the new legislative period")
      Thread.sleep(2000)
      p.success(TaxCut(20))
      println("We reduced the taxes! You must reelect us")
    }
    p.future
  }
  def main(args: Array[String]): Unit = {
    val taxCut = Promise[TaxCut]()
    val tacCut2: Promise[TaxCut] = Promise()
    val taxCutF: Future[TaxCut] = taxCut.future
    taxCut.success(TaxCut(20))
    println(taxCutF.isCompleted)

    val tax: Future[TaxCut] = redeemChampignPledge()
    Thread.sleep(2000)
    println("Now that they're elected")
    tax.onComplete {
      case Success(TaxCut(reduction)) => println(s"A miracle")

      case Failure(ex) => println(s"They broke their priomises")
    }
    println(tax.isCompleted)
  }
}

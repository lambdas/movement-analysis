package recognizers

import org.scalatest.{Matchers, FlatSpec}
import models._

class CustomerWalksAroundEventRecognizerSpec extends FlatSpec with Matchers {

  val observer = new CustomerWalksAroundEventRecognizer

  "CustomerWalksAroundEventObserver" should "not recognize event if customer stays for 3 minutes only" in {
    val records = Seq(LogRecord("john's phone", LogRecord.currentUnixTime - 3 * 60, Point(0, 0)))
    observer.observe(_ => records) should be (Nil)
  }

  "CustomerWalksAroundEventObserver" should "not recognize event if customer travels more than 5 meters in last 5 minutes" in {
    val records = Seq(LogRecord("john's phone", LogRecord.currentUnixTime - 5 * 60, Point(0, 0)),
                      LogRecord("john's phone", LogRecord.currentUnixTime - 4 * 60, Point(10, 0)))
    observer.observe(_ => records) should be (Nil)
  }

  "CustomerWalksAroundEventObserver" should "recognize event if customer stays within a 5 meter-circle for a 5 minutes" in {
    val records = Seq(LogRecord("john's phone", LogRecord.currentUnixTime - 5 * 60, Point(0, 0)),
                      LogRecord("john's phone", LogRecord.currentUnixTime - 4 * 60, Point(1, 0)),
                      LogRecord("john's phone", LogRecord.currentUnixTime - 3 * 60, Point(2, 0)))
    observer.observe(_ => records) should be (Seq(CustomerWalksAroundEvent("john's phone", Point(1, 0))))
  }

}

package recognizers

import models._

trait EventRecognizer {

  def observe(listLogRecords: (Long) => Seq[LogRecord]): Seq[Event]

}

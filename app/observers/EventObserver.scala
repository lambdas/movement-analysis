package observers

import models._

trait EventObserver {

  def observe(listLogRecords: (Long) => Seq[LogRecord]): Seq[Event]

}

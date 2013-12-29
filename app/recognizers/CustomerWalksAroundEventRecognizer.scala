package recognizers

import models._

class CustomerWalksAroundEventRecognizer(timeThresholdInSeconds: Long = 5 * 60, radius: Double = 5) extends EventRecognizer {

  override def observe(listLogRecords: (Long) => Seq[LogRecord] = LogRecord.list) =
    ( for (
        (deviceId, records) <- listLogRecords(LogRecord.currentUnixTime - timeThresholdInSeconds).groupBy(_.deviceId);
        center <- walksAround(records)
      ) yield CustomerWalksAroundEvent(deviceId, center)
    ).toSeq

  def walksAround(logRecords: Seq[LogRecord]): Option[Point] = {
    implicit val timeOrdering = Ordering.by[LogRecord, Long](_.unixTime)

    if (LogRecord.currentUnixTime - logRecords.min.unixTime >= timeThresholdInSeconds) {
      val center = logRecordsCenter(logRecords)

      if (logRecords.forall(_.pos.insideCircle(center, radius)))
        Some(center)
      else
        None
    } else {
      None
    }
  }

  def logRecordsCenter(logRecords: Seq[LogRecord]): Point =
    Point(logRecords.map(_.pos.x).sum / logRecords.size, logRecords.map(_.pos.y).sum / logRecords.size)

}

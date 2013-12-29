package models

case class LogRecord(deviceId: String, unixTime: Long, pos: Point)

object LogRecord {

  protected var records = List[LogRecord]()

  def apply(deviceId: String, pos: Point): LogRecord =
    LogRecord(deviceId, currentUnixTime, pos)

  def create(logRecord: LogRecord) {
    records = logRecord :: records
  }

  def list(afterUnixTime: Long = 0): Seq[LogRecord] =
    records.filter(_.unixTime > afterUnixTime)

  def currentUnixTime: Long = System.currentTimeMillis / 1000L

}

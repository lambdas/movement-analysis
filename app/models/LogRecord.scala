package models

case class LogRecord(deviceId: String, unixTime: Long, x: Double, y: Double)

object LogRecord {

  protected var records = List[LogRecord]()

  def apply(deviceId: String, x: Double, y: Double): LogRecord =
    LogRecord(deviceId, currentUnixTime, x, y)

  def create(logRecord: LogRecord) {
    records = logRecord :: records
  }

  def list(afterUnixTime: Long = 0): Seq[LogRecord] =
    records.filter(_.unixTime > afterUnixTime)

  def currentUnixTime: Long = System.currentTimeMillis / 1000L

}

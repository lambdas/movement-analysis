package models

import anorm._
import anorm.SqlParser._

case class LogRecord(id: Pk[Long], deviceId: String, unixTime: Long, x: Double, y: Double)

object LogRecord {

  protected val simple =
    get[Pk[Long]]("id")        ~
    get[String]  ("device_id") ~
    get[Long]    ("time")      ~
    get[Double]  ("x")         ~
    get[Double]  ("y")         map {
      case id ~ deviceId ~ time ~ x ~ y =>
        LogRecord(id, deviceId, time, x, y)
    }

  def apply(deviceId: String, unixTime: Long, x: Double, y: Double): LogRecord =
    LogRecord(NotAssigned, deviceId, unixTime, x, y)

}

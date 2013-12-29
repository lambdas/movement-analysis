package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

case class LogRecord(id: Pk[Long], deviceId: String, unixTime: Long, x: Double, y: Double)

object LogRecord {

  protected val simple =
    get[Pk[Long]]("id")        ~
    get[String]  ("device_id") ~
    get[Long]    ("unix_time") ~
    get[Double]  ("x")         ~
    get[Double]  ("y")         map {
      case id ~ deviceId ~ time ~ x ~ y =>
        LogRecord(id, deviceId, time, x, y)
    }

  def apply(deviceId: String, unixTime: Long, x: Double, y: Double): LogRecord =
    LogRecord(NotAssigned, deviceId, unixTime, x, y)

  def apply(deviceId: String, x: Double, y: Double): LogRecord =
    LogRecord(deviceId, currentUnixTime(), x, y)

  def create(logRecord: LogRecord): LogRecord = DB.withTransaction {
    implicit connection =>

      val id: Long = logRecord.id.getOrElse {
        SQL("select nextval('log_records_id_seq')").as(scalar[Long].single)
      }

      SQL(
        """
          |INSERT INTO log_records (id, device_id, unix_time, x, y)
          |  VALUES ({id}, {deviceId}, {unixTime}, {x}, {y})
        """.stripMargin
      ).on(
        'id       -> id,
        'deviceId -> logRecord.deviceId,
        'unixTime -> logRecord.unixTime,
        'x        -> logRecord.x,
        'y        -> logRecord.y
      ).executeInsert()

      logRecord.copy(id = Id(id))
  }

  def list(afterUnixTime: Long = 0): Seq[LogRecord] =
    DB.withConnection { implicit connection =>
      SQL(
        s"""
           |SELECT * FROM log_records
           |  WHERE unix_time > {afterUnixTime}
         """.stripMargin
      ).on(
        'afterUnixTime -> afterUnixTime
      ).as(simple *)
    }

  def currentUnixTime(): Long = System.currentTimeMillis() / 1000L

}

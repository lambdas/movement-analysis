package controllers.api.v1

import test.AppSpec
import test.ControllerUtils._
import play.api.test.Helpers._
import play.api.libs.json.Json._
import models._

class LogControllerSpec extends AppSpec {

  "POST /api/v1/log" should "return 400 if parameters are missing" in {
    val result = post(s"/api/v1/log")
    result.status should equal (BAD_REQUEST)
    result.json should equal (obj(
      "errors" -> obj(
        "deviceId" -> arr("This field is required"),
        "x"        -> arr("This field is required"),
        "y"        -> arr("This field is required")
      )
    ))
  }

  it should "create log record if all parameters are valid" in {
    val result = post(s"/api/v1/log", obj(
      "deviceId" -> "john's phone",
      "x"        -> "42",
      "y"        -> "42"
    ))

    result.status should equal (OK)

    result.json should equal (obj())

    val newLogRecord = LogRecord.list().head
    newLogRecord.deviceId should be ("john's phone")
    newLogRecord.x should be (42)
    newLogRecord.y should be (42)
    newLogRecord.unixTime should be (LogRecord.currentUnixTime() +- 1)
  }

}

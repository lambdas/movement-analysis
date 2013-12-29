package controllers.api.v1

import test.AppSpec
import test.ControllerUtils._
import play.api.test.Helpers._
import play.api.libs.json.Json._

class EventControllerSpec extends AppSpec {

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

}

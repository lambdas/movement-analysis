package controllers.api.v1

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.libs.json.Json
import models._
import controllers.RichForm._

object LogController extends Controller {

  protected val createForm = Form(
    mapping(
      "deviceId" -> nonEmptyText,
      "pos"      -> mapping(
        "x"        -> of[Double],
        "y"        -> of[Double]
      )(Point.apply)(Point.unapply)
    )(LogRecord.apply)(LogRecord.unapply(_).map(t => (t._1, t._3)))
  )

  def create = Action(parse.json) { implicit request =>
    createForm.bindFromRequest.withSuccess { logRecord =>
      LogRecord.create(logRecord)
      Ok(Json.obj())
    }
  }

}

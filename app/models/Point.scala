package models

import scala.math._

case class Point(x: Double, y: Double) {

  def insideCircle(center: Point, radius: Double) =
    abs(x - center.x) < radius && abs(y - center.y) < radius

}

package models

import play.api.libs.json.{Json, OFormat}

case class Brand(id: Long, name: String)

object Brand {
  implicit val format: OFormat[Brand] = Json.format[Brand]
}

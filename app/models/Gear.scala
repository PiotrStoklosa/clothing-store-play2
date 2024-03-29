package models

import play.api.libs.json.{Json, OFormat}

case class Gear(id: Long, BrandID: Long, name: String, price: Long)

object Gear {
  implicit val format: OFormat[Gear] = Json.format[Gear]
}

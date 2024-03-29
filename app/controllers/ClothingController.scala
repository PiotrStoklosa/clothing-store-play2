package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._

import models.Gear
import services.{BrandService, ClothesService}

@Singleton
class ClothingController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  val brandService = new BrandService()

  val clothesService = new ClothesService()

  def getAllClothesByBrand(brand: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    brandService.findBrandId(brand) match {
      case Some(brandId) =>
        val clothes = clothesService.getClothesByBrand(brandId)
        Ok(Json.toJson(clothes))
      case None =>
        NotFound("Brand not found")
    }
  }

  def getAllClothes: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val allClothes = clothesService.getClothes
    Ok(Json.toJson(allClothes))
  }

  def getGearById(id: Long): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    clothesService.getGearById(id) match {
      case Some(gear) =>
        Ok(Json.toJson(gear))
      case None =>
        NotFound("Gear not found")
    }
  }

  def updateGear(id: Long): Action[JsValue] = Action(parse.json) { implicit request =>
    request.body.validate[Gear].map { updatedGear =>
      clothesService.updateGear(id, updatedGear) match {
        case Some(updated) =>
          Ok(Json.toJson(updated))
        case None =>
          NotFound("Gear not found")
      }
    }.getOrElse(BadRequest("Invalid JSON"))
  }

  def addGear(): Action[JsValue] = Action(parse.json) { implicit request =>
    request.body.validate[Gear].map { newGear =>
      val added = clothesService.addGear(newGear)
      Created(Json.toJson(added))
    }.getOrElse(BadRequest("Invalid JSON"))
  }

  def deleteGear(id: Long): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    clothesService.deleteGear(id) match {
      case Some(_) =>
        Ok("Gear deleted")
      case None =>
        NotFound("Gear not found")
    }
  }

}

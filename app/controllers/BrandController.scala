package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._

import models.Brand
import services.BrandService

@Singleton
class BrandController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  val brandService = new BrandService()

  def index: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok("Hello from Play2")
  }

  def getAllBrands: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val jsonBrands = Json.toJson(brandService.getBrands)
    Ok(jsonBrands)
  }

  def getBrandById(id: Long): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    brandService.getBrandById(id) match {
      case Some(brand) => Ok(Json.toJson(brand))
      case None => NotFound("Brand not found")
    }
  }


  def addBrand(): Action[JsValue] = Action(parse.json) { implicit request =>
    request.body.validate[Brand].map { newBrand =>
      brandService.getBrands.get(newBrand.id) match {
        case Some(_) => Conflict("Brand with this ID already exists")
        case None =>
          brandService.updateBrand(newBrand.id, newBrand)
          Created(Json.toJson(newBrand))
      }
    }.getOrElse(BadRequest("Invalid JSON"))
  }
}

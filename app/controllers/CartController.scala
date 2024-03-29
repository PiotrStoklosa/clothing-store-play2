package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._

import models.CartItem
import services.{ClothesService, CartService}

@Singleton
class CartController @Inject()(val controllerComponents: ControllerComponents, clothesService: ClothesService, cartService: CartService) extends BaseController {

  implicit val cartItemFormat: Format[CartItem] = Json.format[CartItem]

  def addToCart(): Action[JsValue] = Action(parse.json) { implicit request =>
    request.body.validate[CartItem].map { cartItem =>
      clothesService.getGearById(cartItem.productId) match {
        case Some(gear) =>
          val updatedCart = cartService.addToCart(gear, cartItem.quantity)
          Ok(Json.toJson(updatedCart))
        case None =>
          NotFound("Gear not found")
      }
    }.getOrElse(BadRequest("Invalid JSON"))
  }

  def removeFromCart(productId: Long): Action[AnyContent] = Action { implicit request =>
    val updatedCart = cartService.removeFromCart(productId)
    Ok(Json.toJson(updatedCart))
  }

  def updateCartItem(productId: Long): Action[JsValue] = Action(parse.json) { implicit request =>
    request.body.validate[Int].map { quantity =>
      val updatedCart = cartService.updateCartItem(productId, quantity)
      Ok(Json.toJson(updatedCart))
    }.getOrElse(BadRequest("Invalid JSON"))
  }

  def getCart: Action[AnyContent] = Action { implicit request =>
    val cart = cartService.getCart
    Ok(Json.toJson(cart))
  }

}

package services

import models.{Gear, CartItem}

import scala.collection.mutable

class CartService {
  private val cart: mutable.Map[Long, CartItem] = mutable.Map()

  def addToCart(gear: Gear, quantity: Int): Map[Long, CartItem] = {
    val updatedQuantity = cart.get(gear.id) match {
      case Some(item) => item.quantity + quantity
      case None => quantity
    }
    cart.update(gear.id, CartItem(gear.id, gear.name, gear.price, updatedQuantity))
    cart.toMap
  }

  def removeFromCart(productId: Long): Map[Long, CartItem] = {
    cart.remove(productId)
    cart.toMap
  }

  def updateCartItem(productId: Long, quantity: Int): Map[Long, CartItem] = {
    cart.get(productId) match {
      case Some(item) =>
        val updatedItem = item.copy(quantity = quantity)
        cart.update(productId, updatedItem)
      case None =>
    }
    cart.toMap
  }

  def getCart: Map[Long, CartItem] = cart.toMap
}

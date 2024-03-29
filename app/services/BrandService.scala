package services

import models.Brand

import scala.collection.mutable

class BrandService() {
  def updateBrand(id: Long, newBrand: Brand): Unit = brands.update(id, newBrand)

  def getBrandById(id: Long): Option[Brand] = brands.get(id)


  private val brands: mutable.Map[Long, Brand] = mutable.Map(
    1L -> Brand(1L, "Nike"),
    2L -> Brand(2L, "Adidas"),
    3L -> Brand(3L, "Puma")
  )

  def findBrandId(brandName: String): Option[Long] = {
    brands.find { case (_, brand) => brand.name == brandName }.map(_._1)
  }



  def getBrands: mutable.Map[Long, Brand] = {
    brands
  }
}
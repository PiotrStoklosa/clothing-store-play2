package services

import models.Gear

import scala.collection.mutable

class ClothesService {
  def addGear(newGear: Gear)= clothes.addOne(clothes.size + 1, newGear)

  def deleteGear(id: Long): Option[Gear] = clothes.remove(id)

  def updateGear(id: Long, updatedGear: Gear): Option[Gear] = clothes.put(id, updatedGear)

  def getGearById(id: Long): Option[Gear] = clothes.get(id)


  private val clothes: mutable.Map[Long, Gear] = mutable.Map(
    1L -> Gear(1L, 1L, "t-shirt classic", 50),
    2L -> Gear(2L, 1L, "Black trousers classic", 120),
    3L -> Gear(3L, 2L, "t-shirt with logo", 70)
  )

  def getClothesByBrand(brand: Long): Seq[Gear] = {
    clothes.values.filter(_.BrandID== brand).toSeq
  }

  def getClothes: mutable.Map[Long, Gear] = clothes

}

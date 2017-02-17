package models.data

/**
  * This class is the data model we can handle from post request
  * Here we don't save this model, it's just for using some computation of tos formula
  */

import _root_.utils.KeyGenerator
import models.equipments._
import models.stats.MainStat
import reactivemongo.bson._
import spray.json.DefaultJsonProtocol._

/**
  * Created by stephane on 10/02/2017.
  */



case class Stuff(/*hat: Option[Hat] = None,
                 charm: Option[Charm] = None,
                 necklace: Option[Necklace] = None,
                 rings: List[Ring] = Nil,
                 armorSet: Option[ArmorSet] = None,
                 */firstArm: Option[Weapon] = None/*,
                 secondaryArm: Option[Secondary] = None,
                 costume: Option[Costume] = None,
                 armband: Option[Armband] = None*/
                ) {

  def getTotalEvasion: Int = 0
  def getTotalCriticalResistance: Int = 0
  def getTotalCriticalAttack: Int = 0
  def getTotalCriticalRate: Int = 0

}

case class ArmorSet(shirt: Option[Shirt], pants: Option[Pants], boots: Option[Boots], gloves: Option[Gloves])

case class Build(_id: Option[String] = KeyGenerator.createNewKeyAsString, circleName: String, level: Int, mainStat: MainStat, stuff: Stuff = new Stuff())

object Stuff {
  implicit val stuffHandler = Macros.handler[Stuff]
}

object Build {
  implicit val buildHandler = Macros.handler[Build]
}
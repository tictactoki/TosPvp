package models.equipments

import _root_.utils.KeyGenerator
import models.stats.{DefensiveStat, BasicStat, OffensiveStat, MainStat}
import reactivemongo.bson._

/**
  * Created by stephane on 09/02/2017.
  */

trait Equipment {


  // equipment type like boots or gloves, necessary for json
  val _id: Option[String] = KeyGenerator.createNewKeyAsString
  val `type`: String
  val name: String
  val mainStat: MainStat = MainStat()
  val offensiveStat: OffensiveStat = OffensiveStat()
  val basicStat: BasicStat = BasicStat()
  val defensiveStat: DefensiveStat = DefensiveStat()

}


object Equipment {

  // constant for reader/writer
  final val Type = "type"

  // constant for subclasses
  final val Weapon = "Weapon"
  final val Boots = "Boots"
  final val Gloves = "Gloves"
  final val Pants = "Pants"
  final val Shield = "Shield"
  final val Armband = "Armband"
  final val Artefact = "Artefact"
  final val Charm = "Charm"
  final val Costume = "Costume"
  final val Hat = "Hat"
  final val Necklace = "Necklace"
  final val Ring = "Ring"


}



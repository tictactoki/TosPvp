package models.equipments

import models.stats.{BasicStat, DefensiveStat, OffensiveStat, MainStat}
import reactivemongo.bson.Macros
import utils.KeyGenerator

/**
  * Created by stephane on 15/02/2017.
  */

sealed trait Armor extends Equipment {
  // cloth, leather, plate, ghost
  val category: String
}

case class Boots(override val _id: Option[String] = KeyGenerator.createNewKeyAsString,
                 override val name: String,
                 override val category: String,
                 override val mainStat: MainStat = MainStat(),
                 override val offensiveStat: OffensiveStat = OffensiveStat(),
                 override val defensiveStat: DefensiveStat = DefensiveStat(),
                 override val basicStat: BasicStat = BasicStat(),
                 override val `type`: String = Equipment.Boots) extends Armor

object Boots {
  implicit val bootsHandler = Macros.handler[Boots]
}

case class Gloves(override val _id: Option[String] = KeyGenerator.createNewKeyAsString,
                  override val name: String,
                  override val category: String,
                  override val mainStat: MainStat = MainStat(),
                  override val offensiveStat: OffensiveStat = OffensiveStat(),
                  override val defensiveStat: DefensiveStat = DefensiveStat(),
                  override val basicStat: BasicStat = BasicStat(),
                  override val `type`: String = Equipment.Gloves) extends Armor

object Gloves {
  implicit val glovesHandler = Macros.handler[Gloves]
}

case class Pants(override val _id: Option[String] = KeyGenerator.createNewKeyAsString,
                 override val name: String,
                 override val category: String,
                 override val mainStat: MainStat = MainStat(),
                 override val offensiveStat: OffensiveStat = OffensiveStat(),
                 override val defensiveStat: DefensiveStat = DefensiveStat(),
                 override val basicStat: BasicStat = BasicStat(),
                 override val `type`: String = Equipment.Pants) extends Armor

object Pants

trait Shirt extends Equipment

object Shirt


trait Armband extends Equipment

object Armband

trait Artefact extends Equipment

object Artefact

trait Charm extends Equipment

object Charm

trait Costume extends Equipment

object Costume

trait Hat extends Equipment

case object Hat {

  final val BearEyemask = "BearEyeMask"
  final val BigLionmask = "BigLionMask"


}

trait Necklace extends Equipment

object Necklace

trait Ring extends Equipment

object Ring
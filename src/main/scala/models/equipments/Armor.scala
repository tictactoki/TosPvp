package models.equipments

import models.stats.{BasicStat, DefensiveStat, OffensiveStat, MainStat}

/**
  * Created by stephane on 15/02/2017.
  */

sealed trait ArmorSet extends Equipment {
  // cloth, leather, plate, ghost
  val category: String
}

case class Boots(override val name: String,
                 override val category: String,
                 override val mainStat: MainStat = MainStat(),
                 override val offensiveStat: OffensiveStat = OffensiveStat(),
                 override val defensiveStat: DefensiveStat = DefensiveStat(),
                 override val basicStat: BasicStat = BasicStat(),
                 override val `type`: String = Equipment.Boots) extends ArmorSet {

}


trait Gloves extends Equipment

object Gloves

trait Pants extends Equipment

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
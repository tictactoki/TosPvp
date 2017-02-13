package models.equipments

import models.stats.{DefensiveStat, BasicStat, OffensiveStat, MainStat}

/**
  * Created by stephane on 09/02/2017.
  */

sealed trait Equipment {


  // type of the equipment like boots or gloves, necessary for json
  val `type`: String
  val name: String
  val mainStat: MainStat = MainStat()
  val offensiveStat: OffensiveStat = OffensiveStat()
  val basicStat: BasicStat = BasicStat()
  val defensiveStat: DefensiveStat = DefensiveStat()

}

sealed trait ArmorSet extends Equipment {
  // cloth, leather, plate, ghost
  val category: String
}

sealed trait WeaponSet extends Weapon {
  // slash, stab, hit, magic
  val category: String
}

sealed trait Secondary

sealed trait Primary

sealed trait Weapon extends Equipment {
  val twoHanded: Boolean
}

sealed trait PrimaryWeapon extends WeaponSet with Primary

sealed trait SecondaryWeapon extends WeaponSet with Secondary

sealed trait TwoHandedWeapon extends PrimaryWeapon with Secondary

object PrimaryWeapon {

  def apply(): PrimaryWeapon = Sword("test")

}

object Weapon {
  final val Sword = "Sword"
  final val Dagger = "Dagger"
}

object WeaponSet {
  final val Slash = "Slash"
  final val Stab = "Stab"
  final val Hit = "Hit"
  final val Magic = "Magic"
}


case class Sword(override val name: String,
                 override val mainStat: MainStat = MainStat(),
                 override val offensiveStat: OffensiveStat = OffensiveStat(),
                 override val defensiveStat: DefensiveStat = DefensiveStat(),
                 override val basicStat: BasicStat = BasicStat(),
                 override val twoHanded: Boolean = false,
                 override val `type`: String = Weapon.Sword,
                 override val category: String = WeaponSet.Slash
                ) extends PrimaryWeapon


case class Dagger(override val name: String,
                  override val mainStat: MainStat,
                  override val offensiveStat: OffensiveStat,
                  override val defensiveStat: DefensiveStat,
                  override val basicStat: BasicStat,
                  override val twoHanded: Boolean = false,
                  override val `type`: String = Weapon.Dagger,
                  override val category: String = WeaponSet.Stab) extends SecondaryWeapon

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

trait Shield extends Equipment with Secondary

object Shield

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

object Equipment {

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

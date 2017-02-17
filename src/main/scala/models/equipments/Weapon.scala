package models.equipments

import models.stats.{BasicStat, DefensiveStat, OffensiveStat, MainStat}
import reactivemongo.bson.{Macros, BSONDocument, BSONHandler}
import utils.KeyGenerator

/**
  * Created by stephane on 15/02/2017.
  */

sealed trait WeaponSet extends Equipment {

  protected def init = `type` match {
    case Weapon.Sword => (Some(WeaponSet.Slash), false,true,false)
    case Weapon.TwoHandedSpear=> (Some(WeaponSet.Stab), true,true,true)
    case Weapon.TwoHandedSword => (Some(WeaponSet.Slash), true,true,true)
    case Weapon.Dagger => (Some(WeaponSet.Stab), false,false,true)
    case Weapon.Shield => (None,false,false,true)
    case _ => (None,false,false,false)
  }


  val (category: Option[String], twoHanded: Boolean, primary: Boolean, secondary: Boolean) = init
}

case class Weapon(override val _id: Option[String] = KeyGenerator.createNewKeyAsString,
                  override val name: String,
                  override val mainStat: MainStat = MainStat(),
                  override val offensiveStat: OffensiveStat = OffensiveStat(),
                  override val defensiveStat: DefensiveStat = DefensiveStat(),
                  override val basicStat: BasicStat = BasicStat(),
                  override val `type`: String
                 ) extends WeaponSet

/*object PrimaryWeapon {

  implicit object PrimaryWeaponHandler extends BSONHandler[BSONDocument, PrimaryWeapon] {
    override def write(pw: PrimaryWeapon): BSONDocument = pw match {
      case s:Sword => Sword.swordHandler.write(s)
      case _ => throw new Exception("Fail on primary weapon writer")
    }

    override def read(bson: BSONDocument): PrimaryWeapon = {
      bson.getAs[String](Equipment.Type) match {
        case Some(Weapon.Sword) => Sword.swordHandler.read(bson)
        case _ => throw new Exception("Fail on primary weapon reader")
      }
    }
  }


}*/




object Weapon {
  final val Sword = "Sword"
  final val Dagger = "Dagger"
  final val Shield = "Shield"
  final val TwoHandedSword = "TwoHandedSword"
  final val Bow = "Bow"
  final val Staff = "Staff"
  final val Rod = "Rod"
  final val Spear = "Spear"
  final val TwoHandedSpear = "TwoHandedSpear"
  final val Mace = "Mace"
  final val Rapier = "Rapier"
  final val Pistol = "Pistol"
  final val Cannon = "Cannon"
  final val Musket = "Musket"
  final val Crossbow = "Crossbow"

  final val Category = "category"

  implicit val weaponHandler = Macros.handler[Weapon]

}

object WeaponSet {

  final val Slash = "Slash"
  final val Stab = "Stab"
  final val Hit = "Hit"
  final val Magic = "Magic"
  final val Neutral = "Neutral"
}


/*case class Sword(override val _id: Option[String] = KeyGenerator.createNewKeyAsString,
                 override val name: String,
                 override val mainStat: MainStat = MainStat(),
                 override val offensiveStat: OffensiveStat = OffensiveStat(),
                 override val defensiveStat: DefensiveStat = DefensiveStat(),
                 override val basicStat: BasicStat = BasicStat(),
                 override val twoHanded: Boolean = false,
                 override val `type`: String = Weapon.Sword,
                 override val category: String = WeaponSet.Slash
                ) extends PrimaryWeapon

object Sword {
  implicit val swordHandler = Macros.handler[Sword]
}

case class Dagger(override val _id: Option[String] = KeyGenerator.createNewKeyAsString,
                   override val name: String,
                  override val mainStat: MainStat,
                  override val offensiveStat: OffensiveStat,
                  override val defensiveStat: DefensiveStat,
                  override val basicStat: BasicStat,
                  override val twoHanded: Boolean = false,
                  override val `type`: String = Weapon.Dagger,
                  override val category: String = WeaponSet.Stab) extends SecondaryWeapon

case class Shield(override val _id: Option[String] = KeyGenerator.createNewKeyAsString,
                  override val name: String,
                  override val mainStat: MainStat,
                  override val offensiveStat: OffensiveStat,
                  override val defensiveStat: DefensiveStat,
                  override val basicStat: BasicStat,
                  override val twoHanded: Boolean = false,
                  override val `type`: String = Weapon.Shield) extends SecondaryWeapon

object Shield {
  implicit val shieldHandler = Macros.handler[Shield]
}*/
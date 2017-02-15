package models.equipments

import models.stats.{BasicStat, DefensiveStat, OffensiveStat, MainStat}
import reactivemongo.bson.{Macros, BSONDocument, BSONHandler}
import utils.KeyGenerator

/**
  * Created by stephane on 15/02/2017.
  */

sealed trait WeaponSet extends Weapon {
  // slash, stab, hit, magic
  val category: String
}

sealed trait Secondary

sealed trait Primary

sealed trait Weapon extends Equipment {
  val twoHanded: Boolean
}


sealed trait SecondaryWeapon extends WeaponSet with Secondary

sealed trait TwoHandedWeapon extends PrimaryWeapon with Secondary

object TwoHandedWeapon {
  implicit val twoHandedWeaponHandler = Macros.handler[TwoHandedWeapon]
}

sealed trait PrimaryWeapon extends WeaponSet with Primary

object PrimaryWeapon {

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


}


trait Shield extends Equipment with Secondary

object Shield

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


case class Sword(override val _id: Option[String] = KeyGenerator.createNewKeyAsString,
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

case class Dagger(override val name: String,
                  override val mainStat: MainStat,
                  override val offensiveStat: OffensiveStat,
                  override val defensiveStat: DefensiveStat,
                  override val basicStat: BasicStat,
                  override val twoHanded: Boolean = false,
                  override val `type`: String = Weapon.Dagger,
                  override val category: String = WeaponSet.Stab) extends SecondaryWeapon

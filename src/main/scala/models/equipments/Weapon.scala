package models.equipments

import models.stats.{BasicStat, DefensiveStat, OffensiveStat, MainStat}
import reactivemongo.bson.{Macros, BSONDocument, BSONHandler}
import utils.{ConstantsFields, KeyGenerator}

/**
  * Created by stephane on 15/02/2017.
  */

sealed abstract class WeaponSet extends Equipment {

  protected def init = `type` match {
    case ConstantsFields.Sword => (Some(ConstantsFields.Slash), false,true,false)
    case ConstantsFields.TwoHandedSpear=> (Some(ConstantsFields.Stab), true,true,true)
    case ConstantsFields.TwoHandedSword => (Some(ConstantsFields.Slash), true,true,true)
    case ConstantsFields.Dagger => (Some(ConstantsFields.Stab), false,false,true)
    case ConstantsFields.Shield => (None,false,false,true)
    case _ => (None,false,false,false)
  }


  val (category: Option[String], twoHanded: Boolean, primary: Boolean, secondary: Boolean) = init
}

case class Weapon(override val _id: Option[String] = KeyGenerator.createNewKeyAsString,
                  override val typeName: String = ConstantsFields.Weapon,
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
  implicit val weaponHandler = Macros.handler[Weapon]
}
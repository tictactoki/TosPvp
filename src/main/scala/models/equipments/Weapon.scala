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


  lazy val (category: Option[String], twoHanded: Boolean, primary: Boolean, secondary: Boolean) = init
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

object Weapon {
  implicit val weaponHandler = Macros.handler[Weapon]
}
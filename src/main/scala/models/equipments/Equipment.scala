package models.equipments

import _root_.utils.{ConstantsFields, KeyGenerator}
import models.stats.{BasicStat, DefensiveStat, MainStat, OffensiveStat}
import reactivemongo.bson._

/**
  * Created by stephane on 09/02/2017.
  */

trait Equipment {

  val _id: Option[String] = KeyGenerator.createNewKeyAsString
  // equipment type like boots or gloves, necessary for json or slash stab for weapon
  val `type`: String
  val name: String
  val mainStat: MainStat = MainStat()
  val offensiveStat: OffensiveStat = OffensiveStat()
  val basicStat: BasicStat = BasicStat()
  val defensiveStat: DefensiveStat = DefensiveStat()
  // armor or weapon
  val typeName: String
}

object Equipment {

  val callBackException = throw new Exception("This type of equipment doesn't exist")

  implicit object EquipmentReader extends BSONDocumentReader[Equipment] {
    override def read(bson: BSONDocument): Equipment = bson.getAs[String](ConstantsFields.TypeName) match {
      case Some(ConstantsFields.Weapon) => Weapon.weaponHandler.read(bson)
      case Some(ConstantsFields.Armor) => Armor.armorHandler.read(bson)
      case Some(_) | None => callBackException
    }
  }

  implicit object EquipmentWriter extends BSONDocumentWriter[Equipment] {
    override def write(equipment: Equipment): BSONDocument = equipment match {
      case wp: Weapon => Weapon.weaponHandler.write(wp)
      case arm: Armor => Armor.armorHandler.write(arm)
      case _ => callBackException
    }
  }
}


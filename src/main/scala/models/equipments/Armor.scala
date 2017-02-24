package models.equipments

import models.stats.{BasicStat, DefensiveStat, OffensiveStat, MainStat}
import reactivemongo.bson.Macros
import utils.{ConstantsFields, KeyGenerator}

/**
  * Created by stephane on 15/02/2017.
  */


case class Armor(override val _id: Option[String] = KeyGenerator.createNewKeyAsString,
                 override val typeName: String = ConstantsFields.Armor,
                 override val name: String,
                 override val mainStat: MainStat = MainStat(),
                 override val offensiveStat: OffensiveStat = OffensiveStat(),
                 override val defensiveStat: DefensiveStat = DefensiveStat(),
                 override val basicStat: BasicStat = BasicStat(),
                 // cloth, leather, plate, ghost
                 override val `type`: String) extends Equipment {

  def this(e: Equipment) {
    this(e._id,e.typeName,e.name,e.mainStat,e.offensiveStat,e.defensiveStat,e.basicStat,e.`type`)
  }

}


object Armor {
  implicit val armorHandler = Macros.handler[Armor]
}
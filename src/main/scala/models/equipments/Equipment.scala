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




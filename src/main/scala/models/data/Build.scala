package models.data

import models.equipments._
import models.stats.MainStat

/**
  * Created by stephane on 10/02/2017.
  */

case class Stuff(/*hat: Option[Hat] = None,
                 charm: Option[Charm] = None,
                 necklace: Option[Necklace] = None,
                 rings: List[Ring] = Nil,
                 shirt: Option[Shirt] = None,
                 pant: Option[Pant] = None,
                 */firstArm: Option[PrimaryWeapon] = None/*,
                 secondaryArm: Option[Secondary] = None,
                 gloves: Option[Gloves] = None,
                 boots: Option[Boots] = None,
                 costume: Option[Costume] = None,
                 armband: Option[Armband] = None*/
                ) {

  def getTotalEvasion: Int = 0
  def getTotalCriticalResistance: Int = 0
  def getTotalCriticalAttack: Int = 0
  def getTotalCriticalRate: Int = 0

}

case class ArmorSet()

case class Build(id: Option[String] = None, level: Int, mainStat: MainStat, stuff: Stuff = Stuff())
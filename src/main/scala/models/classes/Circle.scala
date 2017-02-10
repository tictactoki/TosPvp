package models.classes


import models.data.Build
import models.equipments.Weapon

import scala.collection.mutable.HashMap

/**
  * Created by stephane on 09/02/2017.
  */
sealed trait Circle {


  val build: Build
  val aoeRatio: Int
  // map for every circles
  lazy val circles = HashMap[String,Int]()


  protected val HPMultiplier: Double
  protected val SPMultiplier: Double
  protected val buffLimit: Int
  protected val blockMultiplier = 1

  protected def initStat = (getBasicStat,getDefensiveStat, getOffensiveStat)

  protected def getDefensiveStat = (getPhysicalDefense, getMagicDefense, getEvasion, getBlock)

  protected def getOffensiveStat = (getPhysicalAttack(), getSecPhysicalAttack(), getMagicAttack(), getAoeRatio(), getBlockPenetration, getCriticalAttack, getCriticalRate)

  protected def getBasicStat = (getHP, getSP, getHPRecovery, getMaxWeightLimit)

  protected def getAoeRatio(weapon: Option[Weapon] = None): Int = aoeRatio + weapon.map(_.aoeRatio).getOrElse(0)

  protected def getHP = (85 * build.mainStat.con + 17 * HPMultiplier * (build.level - 1))

  protected def getSP = (13 * build.mainStat.spr + 6.7 * SPMultiplier * (build.level - 1))

  protected def getHPRecovery = build.mainStat.con + (build.level / 2)

  protected def getSPRecovery = build.mainStat.spr + (build.level / 2)

  //TODO: See stuff
  protected def getPhysicalAttack(weapon: Option[Weapon] = None) = build.mainStat.str + build.level

  //TODO: See stuff
  protected def getSecPhysicalAttack(weapon: Option[Weapon] = None) = build.mainStat

  // TODO: see Stuff
  protected def getMagicAttack(weapon: Option[Weapon] = None) = build.mainStat.int + build.level + weapon.map(_.magicAttack).getOrElse(0)

  //TODO: see formula with Stuff
  protected def getCriticalAttack = build.mainStat.str

  //TODO: see with Stuff
  protected def getCriticalRate = build.mainStat.dex

  // TODO: see with Stuff
  protected def getCriticalResistance = build.mainStat.con

  protected def getEvasion = build.mainStat.dex + build.level

  protected def getAccuracy = build.level + build.mainStat.dex

  //TODO: see Stuff
  protected def getMagicAmplification = getMagicAttack()

  // To determine the damage taken after DEF, the basic formula is dmg = atk - def
  protected def getPhysicalDefense = build.level / 2

  // To determine the damage taken after MDEF, the basic formula is mdmg = matk - mdef
  protected def getMagicDefense = build.level / 2 + build.mainStat.spr / 5

  protected def getBlock = build.level / 2 + build.mainStat.con + (0.03 * build.level)

  protected def getBlockPenetration = build.level / 2 + build.mainStat.spr

  protected def getMaxWeightLimit = 5000 + 5 * build.mainStat.con + 5 * build.mainStat.str

  protected def getMovementSpeed = 30

}

/**
  *
  * @param name
  * @param unLockedRank
  * Case class to know on which levels the circle has been unlocked
  * The interval of unLockedRank is between 0 to 3, 1 point is equivalent of 15 skill points
  */
case class CircleStep(name: String, unLockedRank: Int)


case class Swordsman(override val build: Build) extends Circle {
  override val aoeRatio: Int = 4
  override protected val HPMultiplier: Double = 3.3
  override protected val buffLimit: Int = 7
  override protected val SPMultiplier: Double = 0.8
}

case class Archer(override val build: Build) extends Circle {
  override val aoeRatio: Int = 0
  override protected val HPMultiplier: Double = 1.4
  override protected val buffLimit: Int = 5
  override protected val SPMultiplier: Double = 0.9
}

case class Cleric(override val build: Build) extends Circle {
  override val aoeRatio: Int = 3
  override protected val HPMultiplier: Double = 1.5
  override protected val buffLimit: Int = 7
  override protected val SPMultiplier: Double = 1.2
}

case class Wizard(override val build: Build) extends Circle {
  override val aoeRatio: Int = 3
  override protected val HPMultiplier: Double = 1.1
  override protected val buffLimit: Int = 5
  override protected val SPMultiplier: Double = 1.2
}
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


  protected val HPMultiplier: Int
  protected val SPMultiplier: Int
  protected val buffLimit: Int
  protected val blockMultiplier = 1

  protected def getAoeRatio(weapon: Option[Weapon]): Int = aoeRatio + weapon.map(_.aoeRatio).getOrElse(0)

  protected def getHP = (85 * build.mainStat.con + 17 * HPMultiplier * (build.level - 1))

  protected def getSP = (13 * build.mainStat.spr + 6.7 * SPMultiplier * (build.level - 1))

  protected def getHPRecovery = build.mainStat.con + (build.level / 2)

  protected def getSPRecovery = build.mainStat.spr + (build.level / 2)

  protected def getPhysicalAttack(weapon: Option[Weapon]) = build.mainStat.str + build.level

  protected def getSecPhysicalAttack(weapon: Option[Weapon]) = build.mainStat


}

/**
  *
  * @param name
  * @param unLockedRank
  * Case class to know on which levels the circle has been unlocked
  * The interval of unLockedRank is between 0 to 3, 1 point is equivalent of 15 skill points
  */
case class CircleStep(name: String, unLockedRank: Int)


class Swordsman
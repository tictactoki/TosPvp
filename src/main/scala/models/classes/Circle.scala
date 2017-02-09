package models.classes


import models.equipments.Weapon

import scala.collection.mutable.HashMap

/**
  * Created by stephane on 09/02/2017.
  */
sealed trait Circle {

  // map for every circles
  lazy val circles = HashMap[String,Int]()

  protected case class MainStat(str: Long, con: Long, int: Long, spr: Long, dex: Long)

  protected val HPMultiplier: Int
  protected val SPMultiplier: Int
  protected val buffLimit: Int
  protected val  blockMultiplier = 1
  protected def aoeRatio(weapon: Weapon): Int



  protected def getMainStat: MainStat




}



class Swordsman
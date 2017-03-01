package models.data

/**
  * This class is the data model we can handle from post request
  * Here we don't save this model (only build), it's just for using some computation of tos formula
  */

import _root_.utils.KeyGenerator
import models.equipments._
import models.stats.MainStat
import reactivemongo.bson._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by stephane on 10/02/2017.
  */



case class Stuff(_id: Option[String] = KeyGenerator.createNewKeyAsString,
                  hat: Option[Armor] = None,
                 charm: Option[Armor] = None,
                 necklace: Option[Armor] = None,
                 rings: List[Armor] = Nil,
                 armor: Option[Armor] = None,
                 firstHand: Option[Weapon] = None,
                 secondaryHand: Option[Weapon] = None,
                 costume: Option[Armor] = None,
                 armband: Option[Armor] = None
                ) {

  def getTotalEvasion: Int = 0
  def getTotalCriticalResistance: Int = 0
  def getTotalCriticalAttack: Int = 0
  def getTotalCriticalRate: Int = 0

}

case class Build private (_id: Option[String] = KeyGenerator.createNewKeyAsString, circleName: String, level: Int, mainStat: MainStat, stuffId: Option[String] = None) {
  def this(b: Build) = {
    this(circleName = b.circleName, level = b.level, mainStat = b.mainStat, stuffId = b.stuffId)
  }
}
case class NestedBuild private (_id: Option[String], circleName: String, level: Int, mainStat: MainStat, stuff: Option[Stuff] = None) {
  def this(build: Build, stuff: Option[Stuff]) = {
    this(build._id,build.circleName,build.level,build.mainStat, stuff)
  }
}

object NestedBuild {
  def apply(build: Option[Build], stuff: Option[Stuff]) = build match {
    case Some(b) => Some(new NestedBuild(b,stuff))
    case None => None
  }

  def apply(build: Build, stuff: Future[Option[Stuff]]) = {
    stuff.map( s => new NestedBuild(build,s))
  }
}

object Stuff {
  implicit val stuffHandler = Macros.handler[Stuff]
}

object Build {
  implicit val buildHandler = Macros.handler[Build]
}
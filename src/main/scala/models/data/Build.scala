package models.data

/**
  * This class is the data model we can handle from post request
  * Here we don't save this model (only build), it's just for using some computation of tos formula
  */

import _root_.utils.{ConstantsFields, KeyGenerator}
import models.equipments._
import models.persistence.{PersistenceTransformer, PersistenceQuery}
import models.stats.MainStat
import reactivemongo.api.commands.{WriteResult, UpdateWriteResult}
import reactivemongo.bson._
import spray.json.DefaultJsonProtocol._
import db.{BuildController, MongoCollection}
import db.MongoCollection._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by stephane on 10/02/2017.
  */



case class Stuff(hat: Option[Armor] = None,
                 charm: Option[Armor] = None,
                 necklace: Option[Armor] = None,
                 rings: List[Armor] = Nil,
                 armor: Option[Armor] = None,
                 firstArm: Option[Weapon] = None,
                 secondaryArm: Option[Weapon] = None,
                 costume: Option[Armor] = None,
                 armband: Option[Armor] = None
                ) {

  def getTotalEvasion: Int = 0
  def getTotalCriticalResistance: Int = 0
  def getTotalCriticalAttack: Int = 0
  def getTotalCriticalRate: Int = 0

}

case class PersistentStuff(_id: Option[String] = KeyGenerator.createNewKeyAsString,
                            hat: Option[String] = None,
                           charm: Option[String] = None,
                           necklace: Option[String] = None,
                           rings: List[String] = Nil,
                           armor: Option[String] = None,
                           firstArm: Option[String] = None,
                           secondaryArm: Option[String] = None,
                           costume: Option[String] = None,
                           armband: Option[String] = None)

case class Build(_id: Option[String] = KeyGenerator.createNewKeyAsString, circleName: String, level: Int, mainStat: MainStat, stuff: Stuff = new Stuff())

case class PersistentBuild(_id: Option[String] = KeyGenerator.createNewKeyAsString, circleName: String, level: Int, mainStat: MainStat, persistentStuff: PersistentStuff)

object PersistentBuild extends PersistenceTransformer[Build,PersistentBuild] {
  implicit val persistentStuffHandler = Macros.handler[PersistentStuff]
  implicit val persistentBuildHandler = Macros.handler[PersistentBuild]

  override def createRealTypeFromPersistentType(ps: PersistentBuild): Future[Build] = ???

  override def createRealTypeFromPersistentType(ps: List[PersistentBuild]): Future[List[Build]] = ???
}

object PersistentStuff extends PersistenceTransformer[Stuff,PersistentStuff] {
  override def createRealTypeFromPersistentType(ps: PersistentStuff): Future[Stuff] = ???

  override def createRealTypeFromPersistentType(ps: List[PersistentStuff]): Future[List[Stuff]] = ???
}

object Stuff {
  implicit val stuffHandler = Macros.handler[Stuff]
}

object Build {
  implicit val buildHandler = Macros.handler[Build]
}
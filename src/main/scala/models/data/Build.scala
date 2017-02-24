package models.data

/**
  * This class is the data model we can handle from post request
  * Here we don't save this model (only build), it's just for using some computation of tos formula
  */

import _root_.utils.{ConstantsFields, KeyGenerator}
import models.equipments._
import models.persistence.{PersistenceTransformer}
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

case class Build(_id: Option[String] = KeyGenerator.createNewKeyAsString, circleName: String, level: Int, mainStat: MainStat, stuff: Stuff = new Stuff())

object Stuff {
  implicit val stuffHandler = Macros.handler[Stuff]
}

object Build {
  implicit val buildHandler = Macros.handler[Build]
}
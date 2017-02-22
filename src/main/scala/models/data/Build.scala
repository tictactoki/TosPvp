package models.data

/**
  * This class is the data model we can handle from post request
  * Here we don't save this model (only build), it's just for using some computation of tos formula
  */

import _root_.utils.KeyGenerator
import models.equipments._
import models.persistence.PersistenceQuery
import models.stats.MainStat
import reactivemongo.api.commands.{WriteResult, UpdateWriteResult}
import reactivemongo.bson._
import spray.json.DefaultJsonProtocol._
import db.{ MongoCRUDController => MongoCRUD }
import db.MongoCollection._

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

object PersistentBuild extends PersistenceQuery[PersistentBuild,Build] {
  implicit val persistentBuildHandler = Macros.handler[PersistentBuild]

  override def insert(data: PersistentBuild)(implicit BSONDocumentWriter: BSONDocumentWriter[PersistentBuild]): Future[WriteResult] = ???

  override def update(query: BSONDocument, data: PersistentBuild)(implicit BSONDocumentWriter: BSONDocumentWriter[PersistentBuild]): Future[UpdateWriteResult] = ???

  override def get(query: BSONDocument)(implicit BSONDocumentReader: BSONDocumentReader[PersistentBuild]): Future[Option[Build]] = ???

  override def getById(id: String)(implicit BSONDocumentReader: BSONDocumentReader[PersistentBuild]): Future[Option[Build]] = ???

  override def getAll(query: BSONDocument)(implicit BSONDocumentReader: BSONDocumentReader[PersistentBuild]): Future[List[Build]] = {
    for {
      persistentBuilds <- MongoCRUD.getAllBuilds
    } yield {

    }
  }




}

object PersistentStuff extends PersistenceQuery[PersistentStuff,Stuff] {
  implicit val persistentStuffHandler = Macros.handler[PersistentStuff]

  override def insert(data: PersistentStuff)(implicit BSONDocumentWriter: BSONDocumentWriter[PersistentStuff]): Future[WriteResult] = ???

  override def update(query: BSONDocument, data: PersistentStuff)(implicit BSONDocumentWriter: BSONDocumentWriter[PersistentStuff]): Future[UpdateWriteResult] = ???

  override def get(query: BSONDocument)(implicit BSONDocumentReader: BSONDocumentReader[PersistentStuff]): Future[Option[Stuff]] = ???

  override def getById(id: String)(implicit BSONDocumentReader: BSONDocumentReader[PersistentStuff]): Future[Option[Stuff]] = ???

  override def getAll(query: BSONDocument)(implicit BSONDocumentReader: BSONDocumentReader[PersistentStuff]): Future[List[Stuff]] = {
    for {
      persistentStuffs <- MongoCRUD.getAllStuffs
      stuffs <- persistentStuffs.map(getStuff(_)(getEquipement))
    } yield {

    }
  }

  def getEquipement(equipment: Option[String]) = {
    MongoCRUD.getEquipmentById(equipment.getOrElse(""))
  }


  def getStuff(s : PersistentStuff)(f: Option[String] => Future[Option[Equipment]]) = {
    val ff = List(f(s.hat),
      f(s.charm),
      f(s.necklace),
      Future.sequence(s.rings.map(r => f(Some(r)))),
      f(s.armor),
      f(s.firstArm),
      f(s.secondaryArm),
      f(s.costume),
      f(s.armband)
      )
    Future.sequence(ff)
  }


}

object Stuff {
  implicit val stuffHandler = Macros.handler[Stuff]
}

object Build {
  implicit val buildHandler = Macros.handler[Build]
}
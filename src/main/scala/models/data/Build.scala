package models.data

/**
  * This class is the data model we can handle from post request
  * Here we don't save this model (only build), it's just for using some computation of tos formula
  */

import _root_.utils.{ConstantsFields, KeyGenerator}
import models.equipments._
import models.persistence.PersistenceQuery
import models.stats.MainStat
import reactivemongo.api.commands.{WriteResult, UpdateWriteResult}
import reactivemongo.bson._
import spray.json.DefaultJsonProtocol._
import db.{MongoCRUDController => MongoCRUD, MongoCollection}
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

object PersistentBuild extends PersistenceQuery[PersistentBuild,Build] {
  //implicit val persistentBuildHandler = Macros.handler[PersistentBuild]
  implicit val psr = Macros.reader[PersistentStuff]
  implicit val psw = Macros.writer[PersistentStuff]
  implicit val persistentBuildReader = Macros.reader[PersistentBuild]
  implicit val persistentBuildWriter = Macros.writer[PersistentBuild]

  override def insert(data: PersistentBuild)(implicit BSONDocumentWriter: BSONDocumentWriter[PersistentBuild]): Future[WriteResult] = ???

  override def update(query: BSONDocument, data: PersistentBuild)(implicit BSONDocumentWriter: BSONDocumentWriter[PersistentBuild]): Future[UpdateWriteResult] = ???

  override def get(query: BSONDocument)(implicit BSONDocumentReader: BSONDocumentReader[PersistentBuild]): Future[Option[Build]] = ???

  override def getById(id: String)(implicit BSONDocumentReader: BSONDocumentReader[PersistentBuild]): Future[Option[Build]] = ???

  override def getAll(query: BSONDocument = BSONDocument())(implicit BSONDocumentReader: BSONDocumentReader[PersistentBuild]): Future[List[Build]] = {
    for {
      persistentBuilds <- MongoCRUD.getAllBuilds
      l <- getBuild(persistentBuilds)
    } yield {
      l
    }
  }


  implicit def fb(p: PersistentBuild) = {
    PersistentStuff.persistentStuffToStuff(p.persistentStuff).map { s =>
      Build(p._id,p.circleName,p.level,p.mainStat,s)
    }
  }

  def getBuild(l: List[PersistentBuild])(implicit f: PersistentBuild => Future[Build]) = {
    Future.sequence(l.map(f))
  }



}

object PersistentStuff extends PersistenceQuery[PersistentStuff,Stuff] {
  //implicit val persistentStuffHandler = Macros.handler[PersistentStuff]
  implicit val psr = Macros.reader[PersistentStuff]
  implicit val psw = Macros.writer[PersistentStuff]

  /*implicit object PersistentStuffReader extends BSONReader[BSONDocument, PersistentStuff] {
    override def read(bson: BSONDocument): PersistentStuff = {
      persistentStuffHandler.read(bson)
    }
  }*/

  override def insert(data: PersistentStuff)(implicit BSONDocumentWriter: BSONDocumentWriter[PersistentStuff]): Future[WriteResult] = ???

  override def update(query: BSONDocument, data: PersistentStuff)(implicit BSONDocumentWriter: BSONDocumentWriter[PersistentStuff]): Future[UpdateWriteResult] = ???

  override def get(query: BSONDocument)(implicit BSONDocumentReader: BSONDocumentReader[PersistentStuff]): Future[Option[Stuff]] = ???

  override def getById(id: String)(implicit BSONDocumentReader: BSONDocumentReader[PersistentStuff]): Future[Option[Stuff]] = ???

  override def getAll(query: BSONDocument)(implicit BSONDocumentReader: BSONDocumentReader[PersistentStuff]): Future[List[Stuff]] = {
    for {
      persistentStuffs <- MongoCRUD.getAllStuffs
      stuffs <- Future.sequence(persistentStuffs.map(ps => ps: Future[Stuff]))
    } yield {
      stuffs
    }
  }

  implicit def persistentStuffToStuff(p: PersistentStuff): Future[Stuff] = {
    for {
      //head <- p.hat.map(MongoCRUD.getById[Armor](MongoCollection.Equipments, _))
      head <- MongoCRUD.getById[Armor](MongoCollection.Equipments, p.hat.getOrElse(""))
      arm <- MongoCRUD.getById[Armor](MongoCollection.Equipments, p.armor.getOrElse(""))
      weapon <- MongoCRUD.getById[Weapon](MongoCollection.Equipments, p.firstArm.getOrElse(""))
    } yield {
      new Stuff(hat = head, armor = head, firstArm = weapon)
    }
  }

  implicit def getEquipment(equipment: Option[String]) = {
    MongoCRUD.getEquipmentById(equipment.getOrElse(""))
  }

  def getWeapon(e: Equipment) = e.typeName match {
    case ConstantsFields.Weapon => new Weapon(e)
    case _ => throw new Exception("Not a weapon")
  }

  def getArmor(e:Equipment) = e.typeName match {
    case ConstantsFields.Armor=> new Armor(e)
    case _ => throw new Exception("Not an armor")
  }

  def test(s: PersistentStuff)(f: Option[String] => Future[Option[Equipment]]) = {
    for {
      oFirstArm <- f(s.firstArm)
      armor <- f(s.armor)
    } yield {
      val w = getWeapon(oFirstArm.get)
      val ar = getArmor(armor.get)
      new Stuff(firstArm = Some(w),armor = Some(ar))
    }
  }


  def getStuff(s : List[PersistentStuff])(f: PersistentStuff => Future[Stuff]) = {
    Future.sequence(s.map(f))
  }


}

object Stuff {
  implicit val stuffHandler = Macros.handler[Stuff]
}

object Build {
  implicit val buildHandler = Macros.handler[Build]
}
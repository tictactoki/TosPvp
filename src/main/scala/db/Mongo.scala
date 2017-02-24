package db

import com.typesafe.config.ConfigFactory
import models.User
import models.data.{PersistentStuff, PersistentBuild, Build}
import models.equipments.Equipment
import models.equipments.Equipment._
import models.stats.MainStat
import reactivemongo.api.{Cursor, DefaultCursor, MongoDriver}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}
import utils.ConstantsFields

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by stephane on 14/02/2017.
  */
object MongoConnection {

  import MongoCollection._

  protected lazy val collections = List(Builds, Stats, Equipments, Users, Stuffs)

  protected lazy val mongoCollections: mutable.HashMap[String, Future[BSONCollection]] = initCollection(collections)
  protected lazy val db = initConnection

  protected def getBSONCollection(name: String) = db.map(_.collection[BSONCollection](name))

  protected def initCollection(list: List[String]) = {
    val map: mutable.HashMap[String, Future[BSONCollection]] = mutable.HashMap()
    list.map { name => map.put(name, getBSONCollection(name)) }
    map
  }

  protected def initConnection = {
    val config = ConfigFactory.load()
    val database = config.getString("mongodb.database")
    val servers = config.getStringList("mongodb.servers").asScala
    val driver = new MongoDriver()
    val connection = driver.connection(servers)
    connection.database(database)
  }

  def getCollection(name: String) = mongoCollections.getOrElse(name, throw new Exception("This collection is not accessible or doesn't exist"))


}

object MongoCRUDController {
  import MongoConnection._
  import MongoCollection._

  protected lazy val queryId = (id: String) => BSONDocument(ConstantsFields.Id-> id)
  val query =  (fieldName: String, value: String) => BSONDocument(fieldName -> value)

  protected def failHandler[T]: Cursor.ErrorHandler[List[T]] = {
    (last: List[T], error: Throwable) =>
      if(last.isEmpty) {
        Cursor.Cont(last)
      }
      else Cursor.Fail(error)
  }

  protected def insert[T](collectionName: String, data: T)(implicit BSONDocumentWriter: BSONDocumentWriter[T]) = {
     getCollection(collectionName).flatMap(_.insert[T](data))
  }

  protected def getAll[T](collectionName: String, query: BSONDocument)(implicit BSONDocumentReader: BSONDocumentReader[T]) = {
    getCollection(collectionName).flatMap { collection =>
      collection.find(query).cursor[T]().collect[List](Int.MaxValue, failHandler[T])
    }
  }

  protected def get[T](collectionName: String, query: BSONDocument)(implicit BSONDocumentReader: BSONDocumentReader[T]) = {
    getCollection(collectionName).flatMap { collection =>
      collection.find(query).one[T]
    }
  }

  def getById[T](collectionName: String, id: String)(implicit BSONDocumentReader: BSONDocumentReader[T]) = get[T](collectionName,queryId(id))


  protected def update[T](collectionName: String, query: BSONDocument, data: T)(implicit BSONDocumentWriter: BSONDocumentWriter[T]) = {
    getCollection(collectionName).flatMap { collection =>
      collection.update(query,data)
    }
  }

  protected def delete[T](collectionName: String, id: String) = getCollection(collectionName).flatMap(_.remove(queryId(id)))

  // Get All data from model
  def getAllBuilds = getAll[PersistentBuild](Builds,BSONDocument())
  def getAllUsers = getAll[User](Users,BSONDocument())
  def getAllStats = getAll[MainStat](Stats,BSONDocument())
  def getAllEquipments = getAll[Equipment](Equipments, BSONDocument())
  def getAllStuffs = getAll[PersistentStuff](Stuffs, BSONDocument())


  // Insert data on collections
  def insertBuild(build: PersistentBuild) = insert[PersistentBuild](Builds,build)
  def insertEquipment(equipment: Equipment) = insert[Equipment](Equipments,equipment)
  def insertStat(stat: MainStat) = insert[MainStat](Stats,stat)
  def insertPS(stuff: PersistentStuff) = insert[PersistentStuff](Stuffs,stuff)

  // Get data
  def getBuild(query: BSONDocument) = get[Build](Builds,query)
  def getStat(query: BSONDocument) = get[MainStat](Stats,query)
  def getEquipment(query: BSONDocument) = get[Equipment](Equipments,query)

  def getBuilds(query: BSONDocument) = getAll[Build](Builds,query)
  def getStats(query: BSONDocument) = getAll[MainStat](Stats,query)
  def getEquipments(query: BSONDocument) = getAll[Equipment](Equipments,query)

  val getBuildById = (id: String) => getBuild(queryId(id))
  val getStatById = (id: String) => getStat(queryId(id))
  val getEquipmentById = (id: String) => getEquipment(queryId(id))




}


object MongoCollection {

  // Build created by users
  final val Builds = "builds"
  final val Stuffs = "stuffs"
  // Only main stats
  final val Stats = "stats"
  // Every equipments
  final val Equipments = "equipments"
  final val Users = "users"
  final val Skills = "skills"
  final val Circles = "circles"

}
package db

import com.typesafe.config.ConfigFactory
import reactivemongo.api.{Cursor, DefaultCursor, MongoDriver}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocumentReader, BSONDocument, BSONDocumentWriter}

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by stephane on 14/02/2017.
  */
object MongoConnection {

  import MongoCollection._

  protected lazy val collections = List(Builds, Stats, Equipments, Users)

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

  protected lazy val queryId = (id: String) => BSONDocument("_id" -> id)

  protected def failHandler[T]: Cursor.ErrorHandler[List[T]] = {
    (last: List[T], error: Throwable) =>
      if(last.isEmpty) {
        Cursor.Cont(last)
      }
      else Cursor.Fail(error)
  }

  def insert[T](collectionName: String, data: T)(implicit BSONDocumentWriter: BSONDocumentWriter[T]) = {
     getCollection(collectionName).flatMap(_.insert[T](data))
  }

  def getAll[T](collectionName: String, query: BSONDocument)(implicit BSONDocumentReader: BSONDocumentReader[T]) = {
    getCollection(collectionName).flatMap { collection =>
      collection.find(query).cursor[T]().collect[List](Int.MaxValue, failHandler[T])
    }
  }

  def get[T](collectionName: String, query: BSONDocument)(implicit BSONDocumentReader: BSONDocumentReader[T]) = {
    getCollection(collectionName).flatMap { collection =>
      collection.find(query).one[T]
    }
  }

  def getById[T](collectionName: String, id: String)(implicit BSONDocumentReader: BSONDocumentReader[T]) = get[T](collectionName,queryId(id))


  def update[T](collectionName: String, query: BSONDocument, data: T)(implicit BSONDocumentWriter: BSONDocumentWriter[T]) = {
    getCollection(collectionName).flatMap { collection =>
      collection.update(query,data)
    }
  }

  def delete[T](collectionName: String, id: String) = getCollection(collectionName).flatMap(_.remove(queryId(id)))

}


object MongoCollection {

  final val Builds = "builds"
  final val Stats = "stats"
  final val Equipments = "equipments"
  final val Users = "users"


}
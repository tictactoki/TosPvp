package db

import com.typesafe.config.ConfigFactory
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.commands.{MultiBulkWriteResult, UpdateWriteResult, WriteResult}
import reactivemongo.api.{Cursor, MongoDriver}
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

trait MongoCrud[T] {
  protected lazy val queryId = (id: Option[String]) => BSONDocument(ConstantsFields.Id -> id.getOrElse(""))

  protected val mainCollection: Future[BSONCollection]

  protected def failHandler: Cursor.ErrorHandler[List[T]] = {
    (last: List[T], error: Throwable) =>
      if (last.isEmpty) {
        Cursor.Cont(last)
      }
      else Cursor.Fail(error)
  }

  def insert(data: T)(implicit BSONDocumentWriter: BSONDocumentWriter[T]): Future[WriteResult] = {
    mainCollection.flatMap{ c => c.insert[T](data) }
  }

  def insert(data: List[T])(implicit bSONDocumentWriter: BSONDocumentWriter[T]): Future[MultiBulkWriteResult] = {
    val stream = data.toStream.map(bSONDocumentWriter.write(_))
    mainCollection.flatMap(_.bulkInsert(stream, ordered = true))
  }

  def find(query: BSONDocument = BSONDocument())(implicit BSONDocumentReader: BSONDocumentReader[T]): Future[List[T]] = {
    mainCollection.flatMap { collection =>
      collection.find(query).cursor[T]().collect[List](Int.MaxValue, failHandler)
    }
  }

  def findOne(query: BSONDocument = BSONDocument())(implicit BSONDocumentReader: BSONDocumentReader[T]): Future[Option[T]] = {
    mainCollection.flatMap { collection =>
      collection.find(query).one[T]
    }
  }

  def findById(id: Option[String])(implicit BSONDocumentReader: BSONDocumentReader[T]): Future[Option[T]] = findOne(queryId(id))


  def update(query: BSONDocument, data: T)(implicit BSONDocumentWriter: BSONDocumentWriter[T]): Future[UpdateWriteResult] = {
    mainCollection.flatMap { collection =>
      collection.update(query, data)
    }
  }

  def delete(id: Option[String]): Future[WriteResult] = mainCollection.flatMap(_.remove(queryId(id)))
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
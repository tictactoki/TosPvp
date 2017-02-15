package db

import com.typesafe.config.ConfigFactory
import reactivemongo.api.MongoDriver
import reactivemongo.api.collections.bson.BSONCollection

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

  protected lazy val db = initConnection
  protected lazy val mongoCollections: mutable.HashMap[String, Future[BSONCollection]] = initCollection(collections)

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


object MongoCollection {

  final val Builds = "builds"
  final val Stats = "stats"
  final val Equipments = "equipments"
  final val Users = "users"


}
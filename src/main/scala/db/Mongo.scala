package db

import com.typesafe.config.ConfigFactory
import reactivemongo.api.MongoDriver
import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by stephane on 14/02/2017.
  */
object Mongo {

  val config = ConfigFactory.load()
  val database = config.getString("mongodb.database")
  val servers = config.getStringList("mongodb.servers").asScala


  val driver = new MongoDriver()
  val connection = driver.connection(servers)
  val db = connection.database(database)
}


object MongoCollection {

  final val Builds = "builds"
  final val Stats = "stats"
  final val Equipments = "equipments"
  final val Users = "users"


}
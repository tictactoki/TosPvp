package servers

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.stream.ActorMaterializer
import akka.http.scaladsl._
import akka.http.scaladsl.server.Directives._
import db.{MongoCollection, MongoConnection, Mongo}
import models.User
import models.equipments.Sword
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{Macros, BSONDocument, BSONHandler}


import scala.concurrent.Future
import scala.io.StdIn
import models.data.{Stuff, Build}
import models.stats.MainStat
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import formats.JsonFormat._
import models.User._
import models.data.Build._
import models.data.Stuff._

/**
  * Created by stephane on 08/02/2017.
  */
object WebServer extends App {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val sword = Sword("test")

  val stuff = Stuff(Some(sword))

  val user = User("test")
  val build = Build(Some("1"),level = 2, mainStat = MainStat(1,1,1,1,1), stuff = stuff)
  val db = MongoConnection.getCollection(MongoCollection.Builds)
  //Mongo.db.map(_.collection[BSONCollection]("builds")).flatMap(_.insert(Build.buildHandler.write(build)))
  val u = db.flatMap(_.find(BSONDocument()).cursor[Build]().collect[List](3).map { l => l})


  val route =
    path("hello") {
      get {
        complete {
          HttpEntity(ContentTypes.`text/html(UTF-8)`,"<h1>Hello</h1>")
        }
      }
    } ~ path("build") {
      get {
        onSuccess(u){ users =>
          //println(users)
          complete(users)
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(route,"localhost", 8090)

  println("server online ")
  StdIn.readLine()
  bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())

}

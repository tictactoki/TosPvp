package servers

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.stream.ActorMaterializer
import akka.http.scaladsl._
import akka.http.scaladsl.server.Directives._
import db.{MongoCRUDController, MongoCollection, MongoConnection}
import models.User
import models.classes.CircleFactory
import models.equipments.Weapon
import models.equipments.Weapon._
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONObjectID, Macros, BSONDocument, BSONHandler}
import utils.ConstantsFields


import scala.concurrent.Future
import scala.io.StdIn
import models.data.{Stuff, Build}
import models.stats.MainStat
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import formats.JsonFormat._
import models.User._
import models.data.Build._
import models.data.Stuff._
import spray.json.DefaultJsonProtocol._
import spray.json._

/**
  * Created by stephane on 08/02/2017.
  */
object WebServer extends App {

  import Build._

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val weapon = new Weapon(name = "test", `type` = ConstantsFields.Sword)

  val stuff = new Stuff(Some(weapon))

  val user = User("test")
  val build = new Build(level = 3, circleName = "Archer", mainStat = MainStat(10,15,19,11,1), stuff = stuff)
  val circle = CircleFactory(build)
  MongoCRUDController.insert[Build](MongoCollection.Builds,build)
  val db = MongoConnection.getCollection(MongoCollection.Builds)
  val f = MongoCRUDController.getAll[Build](MongoCollection.Builds, BSONDocument())

  val route =
    path("hello") {
      get {
        complete {
          HttpEntity(ContentTypes.`text/html(UTF-8)`,"<h1>Hello</h1>")
        }
      }
    } ~ path("build") {
      get {
          onSuccess(f) { list =>
            complete(list)
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(route,"localhost", 8090)

  println("server online ")
  StdIn.readLine()
  bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())

}

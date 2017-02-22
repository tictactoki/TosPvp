package servers

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.stream.ActorMaterializer
import akka.http.scaladsl._
import akka.http.scaladsl.server.Directives._
import db.{MongoCRUDController, MongoCollection, MongoConnection}
import models.User
import models.classes.CircleFactory
import models.equipments.{Armor, Weapon}
import models.equipments.Weapon._
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONHandler, BSONObjectID, Macros}
import utils.ConstantsFields

import scala.concurrent.Future
import scala.io.StdIn
import models.data.{Build, Stuff}
import models.stats.MainStat
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import formats.JsonFormat._
import models.User._
import models.data.Build._
import models.data.Stuff._
import models.routes.{BuildRoute, EquipmentRoute}
import spray.json.DefaultJsonProtocol._
import spray.json._

/**
  * Created by stephane on 08/02/2017.
  */
object WebServer extends BuildRoute with EquipmentRoute with App {

  import Build._

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val weapon = new Weapon(name = "test", `type` = ConstantsFields.Sword)
  val armor = new Armor(name = "test", `type` = ConstantsFields.Cloth)

  val stuff = new Stuff(Some(armor), firstArm = Some(weapon))


  val user = User("test")
  val build = new Build(level = 3, circleName = "Cleric", mainStat = MainStat(10, 15, 19, 11, 1), stuff = stuff)
  val circle = CircleFactory(build)
  //val db = MongoConnection.getCollection(MongoCollection.Builds)

  val route =
    path("hello") {
      get {
        complete {
          HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Hello</h1>")
        }
      }
    } ~ buildRoute ~ equipmentRoute


  val bindingFuture = Http().bindAndHandle(route, "localhost", 8090)

  println("server online ")
  StdIn.readLine()
  bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())

}

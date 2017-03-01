package servers

import akka.actor.ActorSystem
import akka.http.scaladsl._
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import db.{StuffController, BuildController, EquipmentController}
import formats.JsonFormat
import models.User
import models.classes.CircleFactory
import models.data.{Build, Stuff}
import models.equipments.{Armor, Weapon}
import models.routes.{StuffRoute, BuildRoute, EquipmentRoute}
import models.stats.MainStat
import utils.ConstantsFields

import scala.io.StdIn

/**
  * Created by stephane on 08/02/2017.
  */
object WebServer extends JsonFormat with BuildRoute with EquipmentRoute with StuffRoute with  App {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  //val weapon = new Weapon(name = "test", `type` = ConstantsFields.Sword)
  //val armor = new Armor(name = "test", `type` = ConstantsFields.Cloth)

  //val stuff = new Stuff(armor = Some(armor), firstHand = Some(weapon))

  val user = User("test")

  //val b = new Build(level = 3, circleName = "Cleric", mainStat = MainStat(10, 15, 19, 11, 1), stuffId = stuff._id)
  //val circle = CircleFactory(b)

  val route = buildRoute ~ equipmentRoute ~ stuffRoute


  val bindingFuture = Http().bindAndHandle(route, "localhost", 8090)

  println("server online ")
  StdIn.readLine()
  bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())

}

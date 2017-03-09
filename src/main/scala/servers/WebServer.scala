package servers

import akka.actor.ActorSystem
import akka.http.scaladsl._
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.model.Uri.{Query, Path}
import akka.http.scaladsl.model.headers.HttpOriginRange
import akka.http.scaladsl.model.{Uri, HttpRequest, ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import ch.megard.akka.http.cors.{HttpHeaderRange, CorsSettings}
import db.{StuffController, BuildController, EquipmentController}
import formats.JsonFormat
import models.User
import models.classes.CircleFactory
import models.data.{Pvp, NestedBuild, Build, Stuff}
import models.equipments.{Armor, Weapon}
import models.routes._
import models.stats.MainStat
import utils.ConstantsFields

import scala.collection.immutable.Seq
import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration
import scala.io.StdIn
import ch.megard.akka.http.cors.CorsDirectives._
import akka.http.scaladsl.model.HttpMethods._

/**
  * Created by stephane on 08/02/2017.
  */
object WebServer extends JsonFormat with BuildRoute with EquipmentRoute with StuffRoute with PvpRoute with CircleRoute with  App {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val seq = Seq(GET, POST, HEAD, OPTIONS, PUT)
 // val allowHeader = HttpHeaderRange.*
  //val allowOrigin = HttpOriginRange.*
  val settings = CorsSettings.defaultSettings.copy(allowedMethods = seq)//, allowedHeaders = allowHeader, allowedOrigins = allowOrigin)

  /*val source = Source.single(HttpRequest(uri = Uri(path = Path("/vls/v1/stations")).withQuery(Query(("apiKey","")))))
  val flow = Http().outgoingConnectionHttps("api.jcdecaux.com")
  val t = source.via(flow).runWith(Sink.ignore)*/

  //val weapon = new Weapon(name = "test", `type` = ConstantsFields.Sword)
  //val armor = new Armor(name = "test", `type` = ConstantsFields.Cloth)

  //val stuff = new Stuff(armor = Some(armor), firstHand = Some(weapon))

  //val user = User("test")

  /*val t = new Build(circleName = "Archer", level = 3, mainStat = MainStat(4,6,7,8,9))
  BuildController.insert(t)*/

  //val circle = CircleFactory(b)

  val route = cors(settings) {
    buildRoute ~ equipmentRoute ~ stuffRoute ~ pvpRoute ~ circleRoute
  }


  val bindingFuture = Http().bindAndHandle(route, "localhost", 8090)

  println("server online ")
  StdIn.readLine()
  bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())

}

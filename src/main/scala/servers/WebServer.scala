package servers

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.stream.ActorMaterializer
import akka.http.scaladsl._
import akka.http.scaladsl.server.Directives._
import models.equipments.Sword


import scala.io.StdIn
import models.data.{Stuff, Build}
import models.stats.MainStat
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import formats.JsonFormat._


/**
  * Created by stephane on 08/02/2017.
  */
object WebServer extends App {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val sword = Sword("test")

  val stuff = Stuff(Some(sword))

 // val user = User("test")
  val build = Build(Some("1"),level = 2, mainStat = MainStat(1,1,1,1,1), stuff = stuff)
  println(build)

  val route =
    path("hello") {
      get {
        complete {
          HttpEntity(ContentTypes.`text/html(UTF-8)`,"<h1>Hello</h1>")
        }
      }
    } ~ path("build") {
      get {
        complete(build)
      }
    }

  val bindingFuture = Http().bindAndHandle(route,"localhost", 8090)

  println("server online ")
  StdIn.readLine()
  bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())

}

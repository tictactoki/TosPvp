package models.routes

import akka.http.scaladsl.server.Directives._
import db.MongoCRUDController
import formats.JsonFormat.buildFormat
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

/**
  * Created by wong on 19/02/17.
  */
trait BuildRoute {

  val buildRoute = path("builds") {
    get {
      onSuccess(MongoCRUDController.getAllBuilds) { builds =>
        complete(builds)
      }
    }
  }

}

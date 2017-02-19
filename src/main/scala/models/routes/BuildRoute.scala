package models.routes

import akka.http.scaladsl.server.Directives._
import db.MongoCRUDController
import formats.JsonFormat.buildFormat
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import utils.ConstantsFields
import MongoCRUDController._

/**
  * Created by wong on 19/02/17.
  */
trait BuildRoute {

  val buildRoute = path("builds") {
    get {
      parameter('circleName.as[String]) { name =>
        onSuccess(getBuilds(query(ConstantsFields.CircleName,name))) { build =>
          complete(build)
        }
      }
    } ~ get {
      parameter('_id.as[String]) { id =>
        onSuccess(getBuildById(id)) { build =>
          complete(build)
        }
      }
    } ~ get {
      onSuccess(getAllBuilds) { builds =>
        complete(builds)
      }
    }
  }

}

package models.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import db.BuildController
import formats.JsonFormat._
import spray.json.DefaultJsonProtocol._

/**
  * Created by wong on 19/02/17.
  */
trait BuildRoute {

  val buildRoute = path("builds") {
    /*get {
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
    } ~ */get {
      onSuccess(BuildController.find()) { builds =>
        complete(builds)
      }
    }
  }

}

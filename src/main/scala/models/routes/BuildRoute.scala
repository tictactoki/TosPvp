package models.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import db.{StuffController, BuildController}
import db.BuildController._
import formats.JsonFormat
import models.data.{NestedBuild, Build}
import spray.json.DefaultJsonProtocol._
import utils.ConstantsFields
import utils.QueryHelpers._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by wong on 19/02/17.
  */
trait BuildRoute { that: JsonFormat =>

  protected def getNestedBuild(build: Option[Build]) = {

  }

  val buildRoute = path("builds") {
    get {
      parameter('circleName.as[String]) { name =>
        onSuccess(find(query(ConstantsFields.CircleName,name))) { build =>
          complete(build)
        }
      }
    } ~ get {
      parameter('_id.as[String]) { id =>
        complete {
          val nestedBuild = for {
            build <- findById(Some(id))
            stuff <- StuffController.findById(build.flatMap(_.stuffId))
          } yield {
            new NestedBuild(build.getOrElse(throw new Exception("Build Not Found")),stuff)
          }
          nestedBuild
        }
      }
    } ~ get {
      onSuccess(find()) { builds =>
        complete(builds)
      }
    } ~ post {
      entity(as[Build]) { build =>
        println(build)
        onSuccess(BuildController.insert(build)) { insert =>
          complete(if(insert.ok) StatusCodes.OK else StatusCodes.BadRequest)
        }
      }
    }
  }

}

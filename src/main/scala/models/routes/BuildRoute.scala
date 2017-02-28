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
import scala.concurrent.Future

/**
  * Created by wong on 19/02/17.
  */
trait BuildRoute { that: JsonFormat =>

  protected def getNestedBuild(f: => Future[Option[Build]]) = {
    for {
      build <- f
      stuff <- getStuffFromBuild(build)
    } yield NestedBuild(build,stuff)
  }

  protected def getStuffFromBuild(build: Option[Build]) = StuffController.findById(build.flatMap(_.stuffId))
  protected def getStuffFromBuild(build: Build) = StuffController.findById(build.stuffId)

  protected def getNestedBuilds(f: => Future[List[Build]]) = {
    for {
      builds <- f
      nestedBuilds <- Future.sequence(builds.map( b => NestedBuild(b,getStuffFromBuild(b))))
    } yield {
      nestedBuilds
    }
  }

  val buildRoute = path("builds") {
    get {
      parameter('circleName.as[String]) { name =>
        complete(getNestedBuilds(find(query(ConstantsFields.CircleName,name))))
      }
    } ~ get {
      parameter('_id.as[String]) { id =>
        complete(getNestedBuild(findById(Some(id))))
      }
    } ~ get {
      complete(getNestedBuilds(find()))
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

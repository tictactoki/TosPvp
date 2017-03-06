package models.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import db.{StuffController, BuildController}
import db.BuildController._
import formats.JsonFormat
import models.data.{Stuff, NestedBuild, Build}
import spray.json.DefaultJsonProtocol._
import utils.ConstantsFields
import utils.QueryHelpers._

import scala.concurrent.Future


/**
  * Created by wong on 19/02/17.
  */
trait BuildRoute { that: JsonFormat =>

  protected val buildRoute = path("builds") {
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
    } ~ put {
      entity(as[Build]) { postBuild =>
        val ns = new Build(postBuild)
        onSuccess(BuildController.insert(ns)) { insert =>
          complete(if(insert.ok) getNestedBuild(findById(ns._id)) else StatusCodes.BadRequest)
        }
      }
    }
  }

}

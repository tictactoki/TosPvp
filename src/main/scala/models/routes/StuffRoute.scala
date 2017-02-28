package models.routes

import formats.JsonFormat
import akka.http.scaladsl.server.Directives._
import db.StuffController._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import formats.JsonFormat
import spray.json.DefaultJsonProtocol._
import utils.ConstantsFields.Id

/**
  * Created by stephane on 28/02/2017.
  */
trait StuffRoute { that: JsonFormat =>

  val stuffRoute = path("stuffs") {
    get {
      parameter(Id.as[String]) { id =>
        complete(findById(Some(id)))
      }
    }
  } ~ get {
    complete(find())
  }

}

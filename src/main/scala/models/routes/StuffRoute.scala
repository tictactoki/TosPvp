package models.routes

import akka.http.scaladsl.server.Directives._
import db.StuffController._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import formats.JsonFormat

/**
  * Created by stephane on 28/02/2017.
  */
trait StuffRoute { that: JsonFormat =>

  val stuffRoute = path("stuffs") {
    get {
      parameter('_id.as[String]) { id =>
        complete(findById(Some(id)))
      }
    } ~ get {
      complete(find())
    }
  }

}

package models.routes

import akka.http.scaladsl.server.Directives._
import db.EquipmentController._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import formats.JsonFormat
import spray.json.DefaultJsonProtocol._

/**
  * Created by wong on 19/02/17.
  */
trait EquipmentRoute {
  that: JsonFormat =>

  val equipmentRoute = path("equipments") {
    get {
      parameter("_id".as[String]) { id =>
        complete(findById(Some(id)))
      }
    } ~ get {
      complete(find())
    }
  }

}

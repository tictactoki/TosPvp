package models.routes

import akka.http.scaladsl.server.Directives._
import db.EquipmentController._
import formats.JsonFormat.EquipmentFormat
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

/**
  * Created by wong on 19/02/17.
  */
trait EquipmentRoute {

  val equipmentRoute = path("equipments") {
    get {
        parameter("_id".as[String]) { id =>
          onSuccess(findById(id)) { equipment =>
            complete(equipment)
          }
        }
      } ~ get {
      onSuccess(find()) { equipments =>
        complete(equipments)
      }
    }
  }

}

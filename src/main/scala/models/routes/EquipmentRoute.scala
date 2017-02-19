package models.routes

import akka.http.scaladsl.server.Directives._
import db.MongoCRUDController
import formats.JsonFormat.EquipmentFormat
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

/**
  * Created by wong on 19/02/17.
  */
trait EquipmentRoute {

  val equipmentRoute = path("equipments") {
    get {
      onSuccess(MongoCRUDController.getAllEquipments) { equipments =>
        complete(equipments)
      }
    }
  }

}

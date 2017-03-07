package models.routes

import akka.http.scaladsl.model.StatusCodes
import db.BuildController
import formats.JsonFormat
import akka.http.scaladsl.server.Directives._
import models.classes.CircleFactory
import models.data.Build
import utils.ConstantsFields
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by stephane on 06/03/2017.
  */
trait CircleRoute { that: JsonFormat =>


  protected val circleRoute = path("circles") {
    get {
      complete(CircleFactory.circles)
    } ~ post {
      entity(as[Build]) { postbuild =>
        val build = new Build(postbuild)
          onSuccess(BuildController.insert(build)) { insert =>
            complete {
              if(insert.ok) {
                BuildController.getNestedBuild(BuildController.findById(build._id)).map {
                  _.map{CircleFactory(_)
                  }
                }
              }
              else StatusCodes.NotAcceptable
            }
          }
      }
    }
  }

}

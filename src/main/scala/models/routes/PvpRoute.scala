package models.routes

import formats.JsonFormat
import akka.http.scaladsl.server.Directives._
import models.data.{NestedBuild, Build, Pvp}
import models.stats.MainStat

/**
  * Created by stephane on 03/03/2017.
  */
trait PvpRoute { that: JsonFormat =>

  val b = new Build(level = 3, circleName = "Cleric", mainStat = MainStat(10, 15, 19, 11, 1))
  val b2 = new Build(level = 43, circleName = "Archer", mainStat = MainStat(10, 15, 19, 11, 40))
  val ns = NestedBuild(Some(b), None)
  val ns2 = NestedBuild(Some(b2), None)
  val pvp = Pvp(ns.get,ns2.get)

  protected val pvpRoute = path("pvp") {
    post {
      entity(as[Pvp]) { pvp =>
        complete(Pvp.getData(pvp.p1,pvp.p2))
      }
    }
    get {
      complete(Pvp.getData(pvp.p1,pvp.p2))
    }
  }

}

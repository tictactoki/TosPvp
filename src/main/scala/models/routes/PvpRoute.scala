package models.routes

import formats.JsonFormat
import akka.http.scaladsl.server.Directives._
import models.data.{PostPvp, NestedBuild, Build, Pvp}
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
      entity(as[PostPvp]) { pvp =>
        val b1 = new Build(pvp.p1)
        val b2 = new Build(pvp.p2)
        val ns1 = NestedBuild(Some(b1),None)
        val ns2 = NestedBuild(Some(b2), None)
        complete{
          for {
            n1 <- ns1
            n2 <- ns2
          } yield {
            Pvp.getData(n1,n2)
          }
        }
      }
    } ~ get {
      complete(PostPvp(b,b2))
    }
  }

}

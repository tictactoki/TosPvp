package com.example

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.testkit.{ TestActors, TestKit, ImplicitSender }
import formats.JsonFormat
import models.classes._
import models.data.Build
import models.equipments.{Armor, Equipment, WeaponSet, Weapon}
import models.stats.MainStat
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll
import spray.json.{JsonParser, JsValue}
import utils.ConstantsFields
import spray.json._
import DefaultJsonProtocol._
import scala.util.parsing.json.JSON

class CircleSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("CircleSpec"))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }


  "A Circle" must {
    "create the Cleric sub circle when the build object specify cleric on circleName" in {
      val build = Build(circleName = CircleFactory.Cleric, level = 1, mainStat = MainStat(1,2,3,4,5))
      val circle = CircleFactory(build)
      circle.isInstanceOf[Cleric] should be (true)
    }

    "create the Archer sub circle when the build object specify cleric on circleName" in {
      val circle = CircleFactory(Build(circleName = CircleFactory.Archer, level = 1, mainStat = MainStat(1,2,3,4,5)))
      circle.isInstanceOf[Archer] should be (true)
    }

    "create the Swordsman sub circle when the build object specify cleric on circleName" in {
      val circle = CircleFactory(Build(circleName = CircleFactory.Swordsman, level = 1, mainStat = MainStat(1,2,3,4,5)))
      circle.isInstanceOf[Swordsman] should be (true)
    }

    "create the Wizard sub circle when the build object specify cleric on circleName" in {
      val circle = CircleFactory(Build(circleName = CircleFactory.Wizard, level = 1, mainStat = MainStat(1,2,3,4,5)))
      circle.isInstanceOf[Wizard] should be (true)
    }

    "throw an exception when the circle name doesn't exist or doesn't match the right name" in {
      a [Exception] should be thrownBy {
        CircleFactory(Build(circleName = "archer", level = 1, mainStat = MainStat(1,2,3,4,5)))
      }
    }

  }

}

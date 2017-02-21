package stats

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import models.classes.{Cleric, CircleFactory}
import models.data.Build
import models.stats.MainStat
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import utils.ConstantsFields

/**
  * Created by stephane on 21/02/2017.
  */
class StatSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("StatSpec"))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  val clericBuild = Build(circleName = CircleFactory.Cleric, level = 3, mainStat = MainStat(8,7,4,8,3))
  val archerBuild = Build(circleName = CircleFactory.Archer, level = 3, mainStat = MainStat(8,6,3,4,9))
  val cleric = CircleFactory(clericBuild)
  val archer = CircleFactory(archerBuild)

  "A Cleric object" must {
    "get the right hp number from a build" in {
      cleric.basicStat.hp should be (646)
    }

    "get the right sp number from a build" in {
      cleric.basicStat.sp should be (125)
    }

  }

  "An Archer object" must {
    "get the right hp number from a build" in {
      archer.basicStat.hp should be (557)
    }

    "get the right sp number from a build" in {
      archer.basicStat.sp should be (64)
    }
  }


}

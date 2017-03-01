package db

import db.MongoConnection._
import _root_.db.MongoCollection._
import models.data.{NestedBuild, Stuff, Build}
import models.equipments.Equipment
import models.stats.MainStat
import reactivemongo.api.collections.bson.BSONCollection
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by stephane on 24/02/2017.
  */
object BuildController extends MongoCrud[Build] {
  import StuffController.getStuffFromBuild
  override protected val mainCollection: Future[BSONCollection] = getCollection(Builds)

  def getNestedBuild(f: => Future[Option[Build]]) = {
    for {
      build <- f
      stuff <- getStuffFromBuild(build)
    } yield NestedBuild(build,stuff)
  }

  def getNestedBuilds(f: => Future[List[Build]]) = {
    for {
      builds <- f
      nestedBuilds <- Future.sequence(builds.map( b => NestedBuild(b,getStuffFromBuild(b))))
    } yield {
      nestedBuilds
    }
  }

}

object EquipmentController extends MongoCrud[Equipment] {
  override protected val mainCollection: Future[BSONCollection] = getCollection(Equipments)
}

object StatController extends MongoCrud[MainStat] {
  override protected val mainCollection: Future[BSONCollection] = getCollection(Stats)
}

object StuffController extends MongoCrud[Stuff] {
  override protected val mainCollection: Future[BSONCollection] = getCollection(Stuffs)

  def getStuffFromBuild(build: Option[Build]): Future[Option[Stuff]] = StuffController.findById(build.flatMap(_.stuffId))

  def getStuffFromBuild(build: Build): Future[Option[Stuff]] = StuffController.findById(build.stuffId)

}
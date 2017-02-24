package db

import models.data.PersistentBuild
import models.equipments.Equipment
import models.stats.MainStat
import reactivemongo.api.collections.bson.BSONCollection

import scala.concurrent.Future
import MongoConnection._
import MongoCollection._

/**
  * Created by stephane on 24/02/2017.
  */
object BuildController extends MongoCrud[PersistentBuild] {
  override val mainCollection: Future[BSONCollection] = getCollection(Builds)
}

object EquipmentController extends MongoCrud[Equipment] {
  override val mainCollection: Future[BSONCollection] = getCollection(Equipments)
}

object StatController extends MongoCrud[MainStat] {
  override val mainCollection: Future[BSONCollection] = getCollection(Stats)
}
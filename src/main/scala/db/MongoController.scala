package db

import db.MongoConnection._
import _root_.db.MongoCollection._
import models.data.Build
import models.equipments.Equipment
import models.stats.MainStat
import reactivemongo.api.collections.bson.BSONCollection

import scala.concurrent.Future

/**
  * Created by stephane on 24/02/2017.
  */
object BuildController extends MongoCrud[Build] {
  override val mainCollection: Future[BSONCollection] = getCollection(Builds)
}

object EquipmentController extends MongoCrud[Equipment] {
  override val mainCollection: Future[BSONCollection] = getCollection(Equipments)
}

object StatController extends MongoCrud[MainStat] {
  override val mainCollection: Future[BSONCollection] = getCollection(Stats)
}
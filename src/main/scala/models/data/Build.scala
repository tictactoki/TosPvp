package models.data

import models.stats.MainStat

/**
  * Created by stephane on 10/02/2017.
  */

case class Build(id: Option[String], level: Int, skillLevel: Int, mainStat: MainStat)

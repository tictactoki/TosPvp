package models.stats

import models.data.{Build, Stuff}
import reactivemongo.bson.{Macros, BSONDocument, BSONHandler}
import spray.json.DefaultJsonProtocol._

/**
  * Created by stephane on 09/02/2017.
  */

sealed trait Stat

case class OffensiveStat(physicalAttack: Long = 0,
                         secondaryPhysicalAttack: Long = 0,
                         magicAttack: Long = 0,
                         aoeAttackRatio: Int = 0,
                         blockPenetration: Long = 0,
                         criticalAttack: Long = 0,
                         criticalRate: Long = 0,
                         magicAmplification: Long = 0,
                         accuracy: Long = 0
                        ) extends Stat

case class DefensiveStat(physicalDefense: Long = 0,
                         magicDefense: Long = 0,
                         evasion: Long = 0,
                         block: Long = 0,
                         blockRate: Long = 0,
                         criticalResistance: Long = 0
                        ) extends Stat

case class BasicStat(hp: Long = 0,
                     sp: Long = 0,
                     hpRecovery: Long = 0,
                     spRecovery: Long = 0,
                     maximumCarryWeight: Long = 0,
                     maximumStamina: Long = 20
                    ) extends Stat

case class MainStat(str: Long = 0, con: Long = 0, int: Long = 0, spr: Long = 0, dex: Long = 0) extends Stat

object OffensiveStat {
  implicit val offensiveStatHandler = Macros.handler[OffensiveStat]
}

object DefensiveStat {
  implicit val defensiveStatHandler = Macros.handler[DefensiveStat]
}

object BasicStat {
  implicit val basicStathandler = Macros.handler[BasicStat]
}

object MainStat {
  implicit val mainStatHandler = Macros.handler[MainStat]
}
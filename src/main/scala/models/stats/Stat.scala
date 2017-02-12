package models.stats

import models.data.{Build, Stuff}
import spray.json.DefaultJsonProtocol._

/**
  * Created by stephane on 09/02/2017.
  */

sealed trait Stat

case class OffensiveStat(physicalAttack: Long,
                         secondaryPhysicalAttack: Long,
                         magicAttack: Long,
                         aoeAttackRatio: Int,
                         blockPenetration: Long,
                         criticalAttack: Long,
                         criticalRate: Long) extends Stat

case class DefensiveStat(physicalDefense: Long,
                         magicDefense: Long,
                         evasion: Long,
                         block: Long) extends Stat

case class BasicStat(hp: Long,
                     sp: Long,
                     hpRecovery: Long,
                     spRecovery: Long,
                     maximumCarryWeight: Long) extends Stat

case class MainStat(str: Long, con: Long, int: Long, spr: Long, dex: Long) extends Stat

object StatFormat {
  implicit val offensiveStatFormat = jsonFormat7(OffensiveStat)
  implicit val defensiveStatFormat = jsonFormat4(DefensiveStat)
  implicit val basicStatFormat = jsonFormat5(BasicStat)
  implicit val mainStatFormat = jsonFormat5(MainStat)
}
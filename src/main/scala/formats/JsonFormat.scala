package formats

import models.User
import models.data.{Stuff, Build}
import models.equipments._
import models.stats.{OffensiveStat, DefensiveStat, BasicStat, MainStat}
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

/**
  * Created by stephane on 08/02/2017.
  */
object JsonFormat {


  implicit val userFormat = jsonFormat1(User)
  implicit val offensiveStatFormat = jsonFormat9(OffensiveStat)
  implicit val defensiveStatFormat = jsonFormat6(DefensiveStat)
  implicit val basicStatFormat = jsonFormat6(BasicStat)
  implicit val mainStatFormat = jsonFormat5(MainStat)
  implicit val swordFormat = jsonFormat8(Sword.apply)
  //implicit val stuffFormat = jsonFormat1(Stuff.apply)
  //implicit val hatFormat = jsonFormat1(Hat.apply)
  //implicit val buildFormat = jsonFormat4(Build.apply)
}

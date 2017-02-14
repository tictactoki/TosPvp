package formats

import models.User
import models.data.{Stuff, Build}
import models.equipments._
import models.stats.{OffensiveStat, DefensiveStat, BasicStat, MainStat}
import spray.json.DefaultJsonProtocol._
import spray.json.{JsValue, RootJsonFormat}

/**
  * Created by stephane on 08/02/2017.
  */
object JsonFormat {


  implicit val userFormat = jsonFormat1(User.apply)
  implicit val offensiveStatFormat = jsonFormat9(OffensiveStat.apply)
  implicit val defensiveStatFormat = jsonFormat6(DefensiveStat.apply)
  implicit val basicStatFormat = jsonFormat6(BasicStat.apply)
  implicit val mainStatFormat = jsonFormat5(MainStat.apply)
  implicit val swordFormat: RootJsonFormat[Sword] = jsonFormat8(Sword.apply)

  implicit object PrimaryWeaponFormat extends RootJsonFormat[PrimaryWeapon] {
    override def read(json: JsValue): PrimaryWeapon = {
      println(json.asJsObject.fields("type").toString())
      json.asJsObject.fields("type").toString() match {
        case Weapon.Sword => swordFormat.read(json)
      }
    }

    override def write(obj: PrimaryWeapon): JsValue = {
      obj match {
        case s: Sword => swordFormat.write(s)
      }
    }
  }

  implicit val stuffFormat = jsonFormat1(Stuff.apply)
  //implicit val hatFormat = jsonFormat1(Hat.apply)
  implicit val buildFormat = jsonFormat4(Build.apply)
}

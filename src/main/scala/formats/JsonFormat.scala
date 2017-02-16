package formats

import models.User
import models.data.{Stuff, Build}
import models.equipments._
import models.stats.{OffensiveStat, DefensiveStat, BasicStat, MainStat}
import spray.json.DefaultJsonProtocol._
import spray.json.{JsString, JsObject, JsValue, RootJsonFormat}

/**
  * Created by stephane on 08/02/2017.
  */
object JsonFormat {


  implicit val userFormat = jsonFormat1(User.apply)
  implicit val offensiveStatFormat = jsonFormat9(OffensiveStat.apply)
  implicit val defensiveStatFormat = jsonFormat6(DefensiveStat.apply)
  implicit val basicStatFormat = jsonFormat6(BasicStat.apply)
  implicit val mainStatFormat = jsonFormat5(MainStat.apply)
  private val weaponFormat = jsonFormat7(Weapon.apply)

  implicit object Format extends RootJsonFormat[Weapon] {
    override def write(obj: Weapon): JsValue = {
      JsObject(
        ""
        "category" -> JsString(obj.category.getOrElse(""))
      )

    }

    override def read(json: JsValue): Weapon = json.asJsObject.fields(Equipment.Type) match {
      case JsString(Weapon.Sword) => weaponFormat.read(json)
    }
  }

  /*implicit object WeaponSetFormat extends RootJsonFormat[WeaponSet] {
    override def read(json: JsValue): WeaponSet = {
      println(json.asJsObject.fields(Equipment.Type).toString())
      json.asJsObject.fields(Equipment.Type).toString() match {
        case Weapon.Sword => swordFormat.read(json)
      }
    }

    override def write(obj: PrimaryWeapon): JsValue = {
      obj match {
        case s: Sword => swordFormat.write(s)
      }
    }
  }*/

  implicit val stuffFormat = jsonFormat1(Stuff.apply)
  //implicit val hatFormat = jsonFormat1(Hat.apply)
  implicit val buildFormat = jsonFormat4(Build.apply)
}

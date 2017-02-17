package formats

import models.User
import models.data.{Stuff, Build}
import models.equipments._
import models.stats.{OffensiveStat, DefensiveStat, BasicStat, MainStat}
import spray.json.DefaultJsonProtocol._
import spray.json._

/**
  * Created by stephane on 08/02/2017.
  */
object JsonFormat {


  implicit val userFormat = jsonFormat1(User.apply)
  implicit val offensiveStatFormat: RootJsonFormat[OffensiveStat] = jsonFormat9(OffensiveStat.apply)
  implicit val defensiveStatFormat: RootJsonFormat[DefensiveStat] = jsonFormat6(DefensiveStat.apply)
  implicit val basicStatFormat: RootJsonFormat[BasicStat] = jsonFormat6(BasicStat.apply)
  implicit val mainStatFormat: RootJsonFormat[MainStat] = jsonFormat5(MainStat.apply)
  private val weaponFormat = jsonFormat7(Weapon.apply)

  implicit object Format extends RootJsonFormat[Weapon] {
    override def write(obj: Weapon): JsValue = {
      JsObject(
        Equipment.Name -> JsString(obj.name),
        Weapon.Category -> obj.category.map(c => JsString(c)).getOrElse(JsNull),
        Equipment.MainStat -> mainStatFormat.write(obj.mainStat),
        Equipment.OffensiveStat -> offensiveStatFormat.write(obj.offensiveStat),
        Equipment.DefensiveStat -> defensiveStatFormat.write(obj.defensiveStat),
        Equipment.BasicStat -> basicStatFormat.write(obj.basicStat),
        Equipment.Type -> JsString(obj.`type`)
      )

    }

    override def read(json: JsValue): Weapon = json.asJsObject.fields(Equipment.Type) match {
      case JsString(Weapon.Sword) => weaponFormat.read(json)
      case _ => throw new Exception("Type weapon doesn't exist")
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
  implicit val buildFormat = jsonFormat5(Build.apply)
}

package formats

import models.User
import models.classes.{CircleFactory, Circle}
import models.data.{Stuff, Build}
import models.equipments._
import models.stats.{OffensiveStat, DefensiveStat, BasicStat, MainStat}
import spray.json.DefaultJsonProtocol._
import spray.json._
import utils.ConstantsFields

import scala.util.Try

/**
  * Created by stephane on 08/02/2017.
  */
object JsonFormat {


  implicit val userFormat = jsonFormat1(User.apply)
  implicit val offensiveStatFormat: RootJsonFormat[OffensiveStat] = jsonFormat9(OffensiveStat.apply)
  implicit val defensiveStatFormat: RootJsonFormat[DefensiveStat] = jsonFormat6(DefensiveStat.apply)
  implicit val basicStatFormat: RootJsonFormat[BasicStat] = jsonFormat6(BasicStat.apply)
  implicit val mainStatFormat: RootJsonFormat[MainStat] = jsonFormat5(MainStat.apply)
  implicit val armorFormat: RootJsonFormat[Armor] = jsonFormat8(Armor.apply)
  val weaponFormat: RootJsonFormat[Weapon] = jsonFormat8(Weapon.apply)

  implicit object EquipmentFormat extends RootJsonFormat[Equipment] {

    protected def apply(typeName: String, jsValue: JsValue): Equipment = typeName match {
      case ConstantsFields.Weapon => WeaponFormat.read(jsValue)
      case ConstantsFields.Armor => armorFormat.read(jsValue)
      case _ => Equipment.callBackException
    }

    override def read(json: JsValue): Equipment = json.asJsObject.fields(ConstantsFields.TypeName) match {
      case JsString(value) => EquipmentFormat(value,json)
      case _ => Equipment.callBackException
    }

    override def write(obj: Equipment): JsValue = obj match {
      case wp: Weapon => WeaponFormat.write(wp)
      case arm: Armor => armorFormat.write(arm)
      case _ => Equipment.callBackException
    }
  }

  implicit object WeaponFormat extends RootJsonFormat[Weapon] {
    override def write(obj: Weapon): JsValue = {

      /*JsObject(
        ConstantsFields.Id -> obj._id.map(id => JsString(id)).getOrElse(JsNull),
        ConstantsFields.Name -> JsString(obj.name),
        ConstantsFields.Category -> obj.category.map(c => JsString(c)).getOrElse(JsNull),
        ConstantsFields.TwoHanded -> JsBoolean(obj.twoHanded),
        ConstantsFields.Primary -> JsBoolean(obj.primary),
        ConstantsFields.Secondary -> JsBoolean(obj.secondary),
        ConstantsFields.MainStat -> mainStatFormat.write(obj.mainStat),
        ConstantsFields.OffensiveStat -> offensiveStatFormat.write(obj.offensiveStat),
        ConstantsFields.DefensiveStat -> defensiveStatFormat.write(obj.defensiveStat),
        ConstantsFields.BasicStat -> basicStatFormat.write(obj.basicStat),
        ConstantsFields.Type -> JsString(obj.`type`)
      )*/
      JsObject(weaponFormat.write(obj).asJsObject().fields + (
        ConstantsFields.Category -> obj.category.map(c => JsString(c)).getOrElse(JsNull),
        ConstantsFields.TwoHanded -> JsBoolean(obj.twoHanded),
        ConstantsFields.Primary -> JsBoolean(obj.primary),
        ConstantsFields.Secondary -> JsBoolean(obj.secondary)
      ))

    }

    override def read(json: JsValue): Weapon = json.asJsObject.fields(ConstantsFields.Type) match {
      case JsString(ConstantsFields.Sword) => weaponFormat.read(json)
      case _ => throw new Exception("Type weapon doesn't exist")
    }
  }

  /*implicit object CircleFormat extends RootJsonFormat[Circle] {
    override def write(obj: Circle): JsValue = {
      JsObject(
        ConstantsFields.BasicStat -> basicStatFormat.write(obj.basicStat),
        ConstantsFields.DefensiveStat -> defensiveStatFormat.write(obj.defensiveStat),
        ConstantsFields.OffensiveStat -> offensiveStatFormat.write(obj.offensiveStat),
        ConstantsFields.Build -> buildFormat.write(obj.build)
      )
    }

    override def read(json: JsValue): Circle = {
      Try (CircleFactory(buildFormat.read(json))).getOrElse(throw new Exception("The json format is not correct" +  json))
    }
  }*/

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

  implicit val stuffFormat: RootJsonFormat[Stuff] = jsonFormat2(Stuff.apply)
  implicit val buildFormat: RootJsonFormat[Build] = jsonFormat5(Build.apply)
}

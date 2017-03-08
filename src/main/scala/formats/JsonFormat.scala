package formats

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import models.User
import models.classes.{FirstCircle, CircleFactory, Circle}
import models.data._
import models.equipments._
import models.stats.{OffensiveStat, DefensiveStat, BasicStat, MainStat}
import spray.json.DefaultJsonProtocol._
import spray.json._
import utils.ConstantsFields

import scala.util.Try

/**
  * Created by stephane on 08/02/2017.
  */
trait JsonFormat extends DefaultJsonProtocol with SprayJsonSupport {


  implicit val userFormat = jsonFormat1(User.apply)
  implicit val offensiveStatFormat: RootJsonFormat[OffensiveStat] = jsonFormat9(OffensiveStat.apply)
  implicit val defensiveStatFormat: RootJsonFormat[DefensiveStat] = jsonFormat6(DefensiveStat.apply)
  implicit val basicStatFormat: RootJsonFormat[BasicStat] = jsonFormat6(BasicStat.apply)
  implicit val mainStatFormat: RootJsonFormat[MainStat] = jsonFormat5(MainStat.apply)
  implicit val armorFormat: RootJsonFormat[Armor] = jsonFormat8(Armor.apply)
  implicit val firstCircleFormat: RootJsonFormat[FirstCircle] = jsonFormat2(FirstCircle.apply)
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

  implicit object CircleFormat extends RootJsonFormat[Circle] {
    override def write(obj: Circle): JsValue = {
      JsObject(
        ConstantsFields.BasicStat -> basicStatFormat.write(obj.basicStat),
        ConstantsFields.DefensiveStat -> defensiveStatFormat.write(obj.defensiveStat),
        ConstantsFields.OffensiveStat -> offensiveStatFormat.write(obj.offensiveStat),
        ConstantsFields.Build -> nestedBuildFormat.write(obj.build)
      )
    }

    override def read(json: JsValue): Circle = {
      Try (CircleFactory(nestedBuildFormat.read(json))).getOrElse(throw new Exception("The json format is not correct" +  json))
    }
  }

  implicit object BuildFormat extends RootJsonFormat[Build] {
    override def read(json: JsValue): Build = {
      buildFormat.read(json)
    }

    override def write(obj: Build): JsValue = buildFormat.write(obj)
  }

  implicit val stuffFormat: RootJsonFormat[Stuff] = jsonFormat10(Stuff.apply)
  val buildFormat: RootJsonFormat[Build] = jsonFormat5(Build.apply)
  implicit val nestedBuildFormat: RootJsonFormat[NestedBuild] = jsonFormat5(NestedBuild.apply)
  implicit val pvpFormat: RootJsonFormat[Pvp] = jsonFormat2(Pvp.apply)
  implicit val circleWithComputeFormat: RootJsonFormat[CircleWithCompute] = jsonFormat4(CircleWithCompute.apply)
  implicit val postPvpFormat: RootJsonFormat[PostPvp] = jsonFormat2(PostPvp.apply)

}

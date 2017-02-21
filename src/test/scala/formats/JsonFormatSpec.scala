package formats

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import formats.JsonFormat._
import models.data.Build
import models.equipments.{Armor, Weapon}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import spray.json._
import DefaultJsonProtocol._

/**
  * Created by stephane on 21/02/2017.
  */
class JsonFormatSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("JsonFormatSpec"))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }


  val jsonBuild = """{"_id":"58ac3d110501000501e94126","circleName":"Cleric","stuff":{"rings":[]},"mainStat":{"dex":5,"con":2,"str":1,"spr":4,"int":3},"level":1}""".parseJson
  val jsonWeapon = """{"name":"weaponTest","_id":"58ac40493301003301cbe268","basicStat":{"spRecovery":0,"hpRecovery":0,"maximumStamina":20,"hp":0,"sp":0,"maximumCarryWeight":0},"twoHanded":false,"secondary":false,"typeName":"Weapon","defensiveStat":{"magicDefense":0,"evasion":0,"criticalResistance":0,"physicalDefense":0,"block":0,"blockRate":0},"category":"Slash","offensiveStat":{"aoeAttackRatio":0,"physicalAttack":0,"secondaryPhysicalAttack":0,"magicAttack":0,"criticalRate":0,"accuracy":0,"blockPenetration":0,"magicAmplification":0,"criticalAttack":0},"mainStat":{"dex":0,"con":0,"str":0,"spr":0,"int":0},"type":"Sword","primary":true}""".parseJson
  val jsonArmor = """{"name":"armorTest","_id":"58ac40df4e01004e01b837af","basicStat":{"spRecovery":0,"hpRecovery":0,"maximumStamina":20,"hp":0,"sp":0,"maximumCarryWeight":0},"typeName":"Armor","defensiveStat":{"magicDefense":0,"evasion":0,"criticalResistance":0,"physicalDefense":0,"block":0,"blockRate":0},"offensiveStat":{"aoeAttackRatio":0,"physicalAttack":0,"secondaryPhysicalAttack":0,"magicAttack":0,"criticalRate":0,"accuracy":0,"blockPenetration":0,"magicAmplification":0,"criticalAttack":0},"mainStat":{"dex":0,"con":0,"str":0,"spr":0,"int":0},"type":"Leather"}""".parseJson

  "An object" must {
    "be instantiated with json value (Weapon object)" in {
      val weapon = WeaponFormat.read(jsonWeapon)
      weapon.isInstanceOf[Weapon] should be(true)
    }

    "be instantiated with json value (Build object)" in {
      val build = buildFormat.read(jsonBuild)
      build.isInstanceOf[Build] should be (true)
    }

    "be instantiated with json value (Armor object)" in {
      val armor = armorFormat.read(jsonArmor)
      armor.isInstanceOf[Armor] should be (true)
    }
  }


}

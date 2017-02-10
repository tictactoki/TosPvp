package models.equipments

/**
  * Created by stephane on 09/02/2017.
  */

sealed trait Equipment {

  val aoeRatio: Int = 0
  val accuracy: Int = 0
  val physicalAttack: Int = 0
  val physicalDefense: Int = 0
  val magicAttack: Int = 0
  val magicDefense: Int = 0
  val magicAmplification: Int = 0
  val sp: Int = 0
  val hp: Int = 0
  val evasion: Int = 0
  val maxStamina: Int = 0
  val movementSpeed: Int = 0
  val criticalResistance: Int = 0
  val hpRecovery: Int = 0
  val blockPenetration: Int = 0
  val block: Int = 0
  val blockRate: Int = 0





  val con: Int = 0
  val spr: Int = 0
  val str: Int = 0
  val int: Int = 0
  val dex: Int = 0



}

trait Secondary

trait Weapon extends Equipment
trait Boots extends Equipment
trait Gloves extends Equipment
trait Pant extends Equipment
trait Shirt extends Equipment
trait Shield extends Equipment with Secondary
trait Armband extends Equipment
trait Artefact extends Equipment
trait Charm extends Equipment
trait Costume extends Equipment
trait Hat extends Equipment
trait Necklace extends Equipment
trait Ring extends Equipment

object Equipment {

  final val Weapon = "Weapon"
  final val Boots = "Boots"
  final val Gloves = "Gloves"
  final val Pant = "Pant"
  final val Shield = "Shield"
  final val Armband = "Armband"
  final val Artefact = "Artefact"
  final val Charm = "Charm"
  final val Costume = "Costume"
  final val Hat = "Hat"
  final val Necklace = "Necklace"
  final val Ring = "Ring"

}
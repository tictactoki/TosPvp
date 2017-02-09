package models.equipments

/**
  * Created by stephane on 09/02/2017.
  */

sealed trait Equipment

trait Weapon extends Equipment
trait Boots extends Equipment
trait Gloves extends Equipment
trait Pant extends Equipment
trait Shield extends Equipment
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
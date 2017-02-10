package models.equipments

/**
  * Created by stephane on 10/02/2017.
  */
sealed trait Dagger extends Weapon with Secondary

case class Karacha() extends Dagger {
  override val dex: Int = 11
  override val physicalAttack = 176
}
case class Arde() extends Dagger {
  override val physicalAttack = 17
}
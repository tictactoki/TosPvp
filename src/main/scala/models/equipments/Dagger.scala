package models.equipments

/**
  * Created by stephane on 10/02/2017.
  */
/*sealed trait Dagger extends Weapon with Secondary

object Dagger {
  import models.equipments.{ Karacha => K }
  import models.equipments.{ Arde => A }

  final val Karacha = "Karacha"
  final val Arde = "Arde"


  def apply(name: String) = name match {
    case Arde => A
    case Karacha => K
    case _ => throw new Exception("Not yet implemented")
  }

}

case object Karacha extends Dagger {
  override val name = Dagger.Karacha
  override val dex: Int = 11
  override val physicalAttack = 176
}
case object Arde extends Dagger {
  override val name = Dagger.Arde
  override val physicalAttack = 17
}*/
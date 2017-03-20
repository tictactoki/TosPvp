package models.data

import models.classes.{Circle, CircleFactory}

/**
  * Created by stephane on 03/03/2017.
  */
case class Pvp(p1: NestedBuild, p2: NestedBuild)
case class PostPvp(p1: Build, p2: Build)
case class CircleWithCompute(c1: Circle, e1: Double, d1: Double, c2: Circle, e2: Double, d2: Double)

object Pvp {

  protected val getCircleFromBuild = (b: NestedBuild) => CircleFactory(b)

  protected lazy val getCircles = (p1: NestedBuild, p2: NestedBuild) => (getCircleFromBuild(p1),getCircleFromBuild(p2))

  protected def getLvlPenalty(l1: Int, l2: Int) = {

  }

  /**
    * primary formula is
    * [[(ATK + ATKSKILL + AMP) × T0 − ((DEF − DEFDEBUFF) × LVPENALTY + RESELEM)] × CRIT + CRITATK + ATKELEM + ATKTYPE] × T1 × T2 × T3 × ENHANCE + BONUS
    * @param c1
    * @param c2
    * @return
    */
  protected def damageCalculation(c1: Circle, c2: Circle) = {
    (c1.offensiveStat.physicalAttack * 1.0) - (c2.defensiveStat.physicalDefense)
  }

  // TODO: formula isn't completed yet, must add externals effects like skills and attributes
  def computePvpFormula(c1: Circle, c2: Circle) = {

  }

  def computePvpEvasion(c1: Circle, c2: Circle) = {
    (c1.defensiveStat.evasion - c2.offensiveStat.accuracy) / ((0.3605 * c1.build.level + 18.64) * (Math.pow(c2.build.level,0.0335)))
  }

  def getData(p1: NestedBuild, p2: NestedBuild) = {
    val (c1,c2) = getCircles(p1,p2)
    CircleWithCompute(c1,computePvpEvasion(c1,c2), damageCalculation(c1,c2),c2,computePvpEvasion(c2,c1), damageCalculation(c2,c1))
  }

}
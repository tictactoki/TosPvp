package models

import reactivemongo.bson._

/**
  * Created by stephane on 08/02/2017.
  */
case class User(name: String)

object User {
  implicit val userHandler = Macros.handler[User]
}
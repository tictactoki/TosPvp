package models

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
/**
  * Created by stephane on 08/02/2017.
  */
final case class User(name: String)

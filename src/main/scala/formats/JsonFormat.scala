package formats

import models.User
import spray.json.DefaultJsonProtocol._
/**
  * Created by stephane on 08/02/2017.
  */
object JsonFormat {

  implicit val userFormat = jsonFormat1(User)

}

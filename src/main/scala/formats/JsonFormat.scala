package formats

import models.User
import models.data.Build
import models.stats.MainStat
import spray.json.DefaultJsonProtocol._
/**
  * Created by stephane on 08/02/2017.
  */
object JsonFormat {

  implicit val userFormat = jsonFormat1(User)
 // implicit val buildFormat = jsonFormat4(Build)

}

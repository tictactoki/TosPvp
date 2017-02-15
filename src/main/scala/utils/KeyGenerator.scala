package utils

import reactivemongo.bson.BSONObjectID

/**
  * Created by stephane on 15/02/2017.
  */
object KeyGenerator {

  def createNewKeyAsString = BSONObjectID.generate().toString()

}

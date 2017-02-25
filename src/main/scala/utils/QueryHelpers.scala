package utils

import reactivemongo.bson.BSONDocument

/**
  * Created by stephane on 25/02/2017.
  */
object QueryHelpers {
  val query =  (fieldName: String, value: String) => BSONDocument(fieldName -> value)
}

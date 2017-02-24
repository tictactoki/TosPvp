package models.persistence

import reactivemongo.api.commands.{UpdateWriteResult, WriteResult}
import reactivemongo.bson.{BSONDocumentReader, BSONDocument, BSONDocumentWriter}

import scala.concurrent.Future

/**
  * Created by stephane on 22/02/2017.
  */
trait PersistenceTransformer[T,P] {

  def createRealTypeFromPersistentType(ps: P): Future[T]
  def createRealTypeFromPersistentType(ps: List[P]): Future[List[T]]

}

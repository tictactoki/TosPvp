package models.persistence

import reactivemongo.api.commands.{UpdateWriteResult, WriteResult}
import reactivemongo.bson.{BSONDocumentReader, BSONDocument, BSONDocumentWriter}

import scala.concurrent.Future

/**
  * Created by stephane on 22/02/2017.
  */
trait PersistenceQuery[T,P] {

  def insert(data: T)(implicit BSONDocumentWriter: BSONDocumentWriter[T]): Future[WriteResult]
  def getAll(query: BSONDocument)(implicit BSONDocumentReader: BSONDocumentReader[T]): Future[List[P]]
  def get(query: BSONDocument)(implicit BSONDocumentReader: BSONDocumentReader[T]): Future[Option[P]]
  def getById(id: String)(implicit BSONDocumentReader: BSONDocumentReader[T]): Future[Option[P]]
  def update(query: BSONDocument, data: T)(implicit BSONDocumentWriter: BSONDocumentWriter[T]): Future[UpdateWriteResult]

}

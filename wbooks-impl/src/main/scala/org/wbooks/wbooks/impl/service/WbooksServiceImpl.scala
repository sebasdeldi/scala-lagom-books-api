package org.wbooks.wbooks.impl.service

import akka.cluster.sharding.typed.scaladsl.ClusterSharding
import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.{PersistentEntityRef, PersistentEntityRegistry}
import org.wbooks.wbooks.api.{Book, WbooksService}
import org.wbooks.wbooks.impl.eventsourcing.{BookCommand, BookEntity, CreateBookCommand, GetBookCommand}

import scala.concurrent.ExecutionContext

/**
  * Implementation of the WbooksService.
  */
class WbooksServiceImpl(
  clusterSharding: ClusterSharding,
  persistentEntityRegistry: PersistentEntityRegistry
)(implicit ec: ExecutionContext)
  extends WbooksService {

  override def createBook(id: String, title: String): ServiceCall[NotUsed, String] = {
    ServiceCall { _ =>
      val book = Book(id, title)
      ref(book.id).ask(CreateBookCommand(book)).map {
        case Done => s"Book $title! has been created."
      }
    }
  }

  override def getBook(id: String): ServiceCall[NotUsed, String] = {
    ServiceCall { _ =>
      ref(id).ask(GetBookCommand(id)).map(book =>
        s"Book for id:$id is ${book.title}")
    }
  }

  def ref(id: String): PersistentEntityRef[BookCommand[_]] = {
    persistentEntityRegistry
      .refFor[BookEntity](id)
  }
}

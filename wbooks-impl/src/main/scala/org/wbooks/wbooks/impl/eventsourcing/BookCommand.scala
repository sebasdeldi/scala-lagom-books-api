package org.wbooks.wbooks.impl.eventsourcing

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import org.wbooks.wbooks.api.Book
import play.api.libs.json.{Format, Json}

trait BookCommand[R] extends ReplyType[R]

case class CreateBookCommand(book: Book) extends BookCommand[Done]

object CreateBookCommand{
  implicit val format: Format[CreateBookCommand] = Json.format
}

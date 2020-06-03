package org.wbooks.wbooks.impl.eventsourcing

import org.wbooks.wbooks.api.Book
import play.api.libs.json.{Format, Json}

case class BookState(book: Option[Book], timeStamp: String)

object BookState{
  implicit val format: Format[BookState] = Json.format
}

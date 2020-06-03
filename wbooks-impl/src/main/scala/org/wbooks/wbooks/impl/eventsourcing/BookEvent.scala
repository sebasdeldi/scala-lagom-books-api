package org.wbooks.wbooks.impl.eventsourcing

import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, AggregateEventTagger}
import org.wbooks.wbooks.api.Book
import play.api.libs.json.{Format, Json}

sealed trait BookEvent extends AggregateEvent[BookEvent] {
  override def aggregateTag: AggregateEventTagger[BookEvent] = BookEvent.Tag
}

object BookEvent {
  val Tag: AggregateEventTag[BookEvent] = AggregateEventTag[BookEvent]
}

case class BookCreated(book: Book) extends BookEvent

object BookCreated{
  implicit val format: Format[BookCreated] = Json.format
}

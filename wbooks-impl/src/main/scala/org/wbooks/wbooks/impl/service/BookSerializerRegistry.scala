package org.wbooks.wbooks.impl.service

import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import org.wbooks.wbooks.api.Book
import org.wbooks.wbooks.impl.eventsourcing.{BookCreated, BookState, CreateBookCommand}

import scala.collection.immutable

object BookSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: immutable.Seq[JsonSerializer[_]] = immutable.Seq(
    JsonSerializer[Book],
    JsonSerializer[BookCreated],
    JsonSerializer[BookState],
    JsonSerializer[CreateBookCommand],
  )
}

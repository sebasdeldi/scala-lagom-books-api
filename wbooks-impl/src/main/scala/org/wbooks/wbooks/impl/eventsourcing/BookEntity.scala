package org.wbooks.wbooks.impl.eventsourcing

import java.time.LocalDateTime

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity

class BookEntity extends PersistentEntity {

  override type Command = BookCommand[_]
  override type Event = BookEvent
  override type State = BookState

  override def initialState = BookState(None, LocalDateTime.now().toString)

  override def behavior: (BookState) => Actions = {
    case BookState(_, _) => Actions()
      .onCommand[CreateBookCommand, Done] {
        case (CreateBookCommand(book), ctx, _) ⇒
          ctx.thenPersist(BookCreated(book))(_ ⇒ ctx.reply(Done))
      }
      .onEvent {
        case (BookCreated(book), _) ⇒
          BookState(Some(book), LocalDateTime.now().toString)
      }
  }
}

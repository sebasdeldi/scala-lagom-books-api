package org.wbooks.wbooks.api

import play.api.libs.json.{Format, Json}

case class Book(id: String, title: String)

object Book{
  implicit val format: Format[Book] = Json.format
}

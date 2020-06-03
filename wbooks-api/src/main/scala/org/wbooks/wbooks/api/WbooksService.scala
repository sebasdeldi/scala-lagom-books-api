package org.wbooks.wbooks.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

trait WbooksService extends Service {

  def createBook(id: String, title: String): ServiceCall[NotUsed, String]

  override final def descriptor: Descriptor = {
    import Service._
    named("wbooks")
      .withCalls(
        restCall(Method.POST, "/api/books/:id/:title", createBook _)
      )
      .withAutoAcl(true)
  }
}

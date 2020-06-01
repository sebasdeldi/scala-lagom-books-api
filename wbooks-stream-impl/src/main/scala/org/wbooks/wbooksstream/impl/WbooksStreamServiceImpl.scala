package org.wbooks.wbooksstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import org.wbooks.wbooksstream.api.WbooksStreamService
import org.wbooks.wbooks.api.WbooksService

import scala.concurrent.Future

/**
  * Implementation of the WbooksStreamService.
  */
class WbooksStreamServiceImpl(wbooksService: WbooksService) extends WbooksStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(wbooksService.hello(_).invoke()))
  }
}

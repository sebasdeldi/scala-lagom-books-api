package org.wbooks.wbooksstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import org.wbooks.wbooksstream.api.WbooksStreamService
import org.wbooks.wbooks.api.WbooksService
import com.softwaremill.macwire._

class WbooksStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new WbooksStreamApplication(context) {
      override def serviceLocator: NoServiceLocator.type = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new WbooksStreamApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[WbooksStreamService])
}

abstract class WbooksStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer: LagomServer = serverFor[WbooksStreamService](wire[WbooksStreamServiceImpl])

  // Bind the WbooksService client
  lazy val wbooksService: WbooksService = serviceClient.implement[WbooksService]
}

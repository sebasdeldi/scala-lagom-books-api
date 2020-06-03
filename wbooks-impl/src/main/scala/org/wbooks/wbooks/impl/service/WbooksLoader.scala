package org.wbooks.wbooks.impl.service

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.jdbc.JdbcPersistenceComponents
import com.lightbend.lagom.scaladsl.persistence.slick.SlickPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import org.wbooks.wbooks.api.WbooksService
import org.wbooks.wbooks.impl.eventsourcing.BookEntity
import play.api.db.HikariCPComponents
import play.api.libs.ws.ahc.AhcWSComponents



class WbooksLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new WbooksApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new WbooksApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[WbooksService])
}

abstract class WbooksApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with JdbcPersistenceComponents
    with HikariCPComponents
    with SlickPersistenceComponents
    with LagomKafkaComponents
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[WbooksService](wire[WbooksServiceImpl])

  //Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = BookSerializerRegistry

  // Register the  persistent entity
  persistentEntityRegistry.register(wire[BookEntity])
}

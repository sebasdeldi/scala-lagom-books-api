organization in ThisBuild := "org.wbooks"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.13.0"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.3" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.1.1" % Test
val postgres = "org.postgresql" % "postgresql" % "42.2.7"

lazy val `wbooks` = (project in file("."))
  .aggregate(`wbooks-api`, `wbooks-impl`)

lazy val `wbooks-api` = (project in file("wbooks-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `wbooks-impl` = (project in file("wbooks-impl"))
  .enablePlugins(LagomScala)
  .settings(
    lagomCassandraEnabled in ThisBuild := false,
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceJdbc,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest,
      postgres
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`wbooks-api`)

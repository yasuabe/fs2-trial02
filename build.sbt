name := "fs2-trial02"
version := "0.1"

scalaVersion := "2.13.1"

val fs2Ver = "2.0.0"
val doobie2Ver = "0.8.2"
resolvers += Resolver.sonatypeRepo("snapshots")

scalacOptions ++= Seq(
  "-encoding", "utf8", // Option and arguments on same line
  "-Xfatal-warnings",  // New lines for each options
  "-deprecation",
  "-unchecked",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps"
)
libraryDependencies ++= Seq(
  "co.fs2" %% "fs2-core" % fs2Ver,
  "co.fs2" %% "fs2-io" % fs2Ver,
  "org.tpolecat" %% "doobie-core" % doobie2Ver,
  "org.tpolecat" %% "doobie-postgres" % doobie2Ver,
  "org.http4s" %% "http4s-core" % "0.21.0-M5",
  "org.http4s" %% "http4s-client" % "0.21.0-M5",
  "org.http4s" %% "http4s-blaze-client" % "0.21.0-M5",
  "org.http4s" %% "jawn-fs2" % "0.15.0",
  "io.circe" %% "circe-core" % "0.12.1",
  "io.circe" %% "circe-jawn" % "0.12.1",
  "com.ovoenergy" %% "fs2-kafka" % "0.20.1"
)

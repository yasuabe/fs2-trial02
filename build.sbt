val scala3Version = "3.1.2"

val fs2Ver     = "3.2.7"
val doobie2Ver = "1.0.0-RC2"
val http4sVer  = "0.23.12"
val circeVer   = "0.15.0-M1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "fs2-trial02",
    version := "0.2",

    scalaVersion := scala3Version,
    resolvers += Resolver.sonatypeRepo("snapshots"),

    scalacOptions ++= Seq(
      "-encoding", "utf8", // Option and arguments on same line
      "-Xfatal-warnings",  // New lines for each options
      "-Ykind-projector:underscores",
      "-deprecation",
      "-unchecked",
      "-language:implicitConversions",
      "-language:higherKinds",
      "-language:existentials",
      "-language:postfixOps"
    ),
    libraryDependencies ++= Seq(
     "org.typelevel"    %% "cats-effect"         % "3.3.11",
      "co.fs2"          %% "fs2-core"            % fs2Ver,
      "co.fs2"          %% "fs2-io"              % fs2Ver,
      "org.tpolecat"    %% "doobie-core"         % doobie2Ver,
      "org.tpolecat"    %% "doobie-postgres"     % doobie2Ver,
      "org.http4s"      %% "http4s-core"         % http4sVer,
      "org.http4s"      %% "http4s-client"       % http4sVer,
      "org.http4s"      %% "http4s-blaze-client" % http4sVer,
      "org.typelevel"   %% "jawn-fs2"            % "2.2.0",
      "io.circe"        %% "circe-core"          % circeVer,
      "io.circe"        %% "circe-jawn"          % circeVer,
      "com.github.fd4s" %% "fs2-kafka" % "3.0.0-M7"

    )
  )
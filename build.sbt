name := "Sensors"

version := "0.1"

scalaVersion := "2.11.7"

packAutoSettings

libraryDependencies ++= Seq(
  "net.java.dev.jna" % "jna" % "4.1.0",
  "com.typesafe.akka" %% "akka-actor" % "2.3.14",
  "com.typesafe.akka" %% "akka-remote" % "2.3.14"
)

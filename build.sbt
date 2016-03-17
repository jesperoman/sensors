name := "Sensors"

version := "0.1"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "net.java.dev.jna" % "jna" % "4.1.0",
  "com.typesafe.akka" %% "akka-actor" % "2.4.0",
  "com.typesafe.akka" %% "akka-remote" % "2.4.0"
)

import sbt.Keys._

val commonDeps = Seq(
  "net.java.dev.jna" % "jna" % "4.1.0",
  "com.typesafe.akka" %% "akka-actor" % "2.3.14",
  "com.typesafe.akka" %% "akka-remote" % "2.3.14"
)

lazy val sensor = (project in file("sensor"))
  .dependsOn(common)
  .settings (
    name := "Sensors",
    version := "0.1",
    scalaVersion := "2.11.7",
    packAutoSettings,
    libraryDependencies ++= commonDeps
  )

lazy val remote = (project in file("remote"))
  .dependsOn(common)
  .settings(
    name := "Remote",
    version := "0.1",
    scalaVersion := "2.11.7",
    packAutoSettings,
    libraryDependencies ++= commonDeps
  )

lazy val common = (project in file("common"))
  .settings(
    name := "SensorCommon",
    version := "0.1",
    scalaVersion := "2.11.7"
  )


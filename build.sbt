name := "akka-tcp"

version := "1.0"

scalaVersion := "2.11.8"

lazy val akkaVersion = "2.4.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % "2.2.6" % "test"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")

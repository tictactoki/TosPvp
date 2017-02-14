name := """TosPvp"""

lazy val root = (project in file("."))

version := "1.0"

scalaVersion := "2.12.1"

scalacOptions := Seq("-deprecation")

val akkaVersion = "2.4.17"

val httpVersion = "10.0.3"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-simple" % "1.6.4",
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-agent" % akkaVersion,
  "com.typesafe.akka" %% "akka-camel" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
  "com.typesafe.akka" %% "akka-contrib" % akkaVersion,
  "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-osgi" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-tck" % akkaVersion,
  "com.typesafe.akka" %% "akka-remote" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-distributed-data-experimental" % akkaVersion,
  "com.typesafe.akka" %% "akka-typed-experimental" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-query-experimental" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-core" % httpVersion,
  "com.typesafe.akka" %% "akka-http" % httpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % httpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % httpVersion,
  "com.typesafe.akka" %% "akka-http-jackson" % httpVersion,
  "com.typesafe.akka" %% "akka-http-xml" % httpVersion,
  "org.reactivemongo" %% "reactivemongo" % "0.12.1",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

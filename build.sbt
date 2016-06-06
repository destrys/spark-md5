name := "spark-md5"

version := "0.1"

scalaVersion := "2.10.4"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core"      % "1.3.0" % "provided",
  "org.scalactic" %% "scalactic" % "2.2.6",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test")

parallelExecution in Test := false

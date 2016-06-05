name := "spark-md5"

version := "0.1"

scalaVersion := "2.10.4"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core"      % "1.3.0" % "provided")


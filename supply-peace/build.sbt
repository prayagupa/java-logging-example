name := "log-collector"

version := "1.0"

scalaVersion := "2.11.8"

// https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.6.2"

// https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api
libraryDependencies += "org.apache.logging.log4j" % "log4j-api" % "2.6.2"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.0"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.4.0"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-annotations" % "2.4.0"
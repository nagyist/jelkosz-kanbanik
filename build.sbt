name := "kanbanik"

version := "0.2.6"

scalaVersion := "2.10.3"

lazy val dto = project.in(file("kanbanik-dto"))

lazy val server = Project(id = "server", base = file("kanbanik-server")) dependsOn(dto)

resolvers += "Scala-tools Maven2 Repository" at "https://oss.sonatype.org/content/groups/scala-tools"

libraryDependencies += "org.apache.shiro" % "shiro-all" % "1.2.2"

libraryDependencies += "net.liftweb" % "lift-json_2.10" % "2.5.1"

libraryDependencies += "javax.servlet" % "servlet-api" % "2.5"

libraryDependencies += "junit" % "junit" % "4.11"

libraryDependencies += "org.hamcrest" % "hamcrest-library" % "1.3"

libraryDependencies += "org.mockito" % "mockito-all" % "1.9.5"

libraryDependencies += "org.mongodb" % "casbah_2.10" % "2.6.3" artifacts( Artifact("casbah_2.10", "pom", "pom") ) 

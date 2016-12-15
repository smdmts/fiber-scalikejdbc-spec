lazy val root = (project in file("."))
  .enablePlugins(JavaAgent, JavaAppPackaging)
  .settings(
    inThisBuild(List(
      organization := "com.github.smdmts",
      scalaVersion := "2.12.0"
    )),
    name := "Hello",
    javaAgents += "co.paralleluniverse" % "quasar-core" % "0.7.7" % "dist;compile;test"
  )

scalafmtConfig in ThisBuild := Some(file(".scalafmt.conf"))

resourceDirectory in Compile := baseDirectory.value / "src/conf"

val scalikejdbcV = "2.5.+"
val skinnyV = "2.3.0"
val akkaV = "2.4.14"

libraryDependencies ++= scakileJdbcDependency ++ constatDependency ++ databaseConnector ++ otherDependency

def otherDependency = Seq(
  "com.iheart" %% "ficus" % "1.3.3",
  "org.scalactic" %% "scalactic" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

def akkaDependency = Seq(
  "com.typesafe.akka"   %% "akka-actor" % akkaV
)

def databaseConnector = Seq(
  "mysql" % "mysql-connector-java" % "5.1.31"
)

def constatDependency = Seq(
  "co.paralleluniverse" % "comsat-jdbc" % "0.7.0",
  "co.paralleluniverse" % "quasar-core" % "0.7.7"
)

def scakileJdbcDependency = Seq(
  "org.scalikejdbc" %% "scalikejdbc"                  % scalikejdbcV,
  "org.scalikejdbc" %% "scalikejdbc-config"           % scalikejdbcV,
  "com.zaxxer" % "HikariCP"                           % "2.4.5",
  "org.skinny-framework"   %% "skinny-orm"            % skinnyV,
  "org.scalikejdbc"   %% "scalikejdbc-test"           % scalikejdbcV   % "test"
)

resolvers ++= Seq(
  "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases" ,
  "Atlassian Releases" at "https://maven.atlassian.com/public/",
  "Artima Maven Repository" at "http://repo.artima.com/releases",
  "sonatype snaoshots repository" at "https://oss.sonatype.org/content/repositories/snapshots/",
  Resolver.jcenterRepo
)

package adapter.datasource.rdb

import java.io.File

import com.typesafe.config.ConfigFactory
import org.scalatest._
import scalikejdbc._
import scalikejdbc.scalatest.AutoRollback

/**
  * AutoRollbackFeature.
  */
trait AutoRollbackFeature extends AutoRollback with BeforeAndAfterAll with BeforeAndAfterEach {
  self: fixture.TestSuite =>
  override def db(): DB = NamedDB(Symbol("default")).toDB()
  override def beforeAll() = {
    SpecDBSettings.loadJDBCSettings
  }
}


object SpecDBSettings {
  lazy val loadJDBCSettings = {
    val f = new File("src/conf/application.conf")
    implicit val config = ConfigFactory.parseFile(f)
    RDBs().initialize()
  }
}

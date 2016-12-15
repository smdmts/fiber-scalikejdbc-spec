package adapter.datasource.rdb

import co.paralleluniverse.fibers.jdbc.FiberDataSource
import com.typesafe.config.{Config, ConfigFactory, ConfigValue}
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import net.ceedubs.ficus.Ficus._
import scalikejdbc.{ConnectionPool, DataSourceConnectionPool, NamedDB}

import scala.collection.JavaConverters._

/**
 * RDBs.
 */
object RDBs {
  def db = NamedDB('default)
}

case class RDBs(implicit config:Config) {

  def initialize(): Unit = {
    config.getConfig("db").root.asScala.foreach {
      case (name, dbConf) =>
        val conf = RDBDefinitions(name, dbConf)
        val datasource = new HikariDataSource(conf.hikariConfig)
        // ConnectionPool.add(Symbol(conf.name), new DataSourceConnectionPool(datasource))
        // use FiberDataSource.
        ConnectionPool.add(Symbol(conf.name), new DataSourceConnectionPool(FiberDataSource.wrap(datasource)))
    }
  }

}

case class RDBDefinitions(name: String, config: ConfigValue) {
  private val root         = ConfigFactory.empty().withFallback(config)
  val driver               = root.as[String]("driver")
  val url                  = root.as[String]("url")
  val userName             = root.as[String]("user")
  val password             = root.as[String]("password")
  val connectionPoolName   = root.as[String]("connectionPoolName")
  val maximumPoolSize      = root.getAs[String]("maximumPoolSize").getOrElse("30")
  val connectionTimeout    = root.getAs[String]("connectionTimeout").getOrElse("30")
  val idleTimeout          = root.getAs[String]("idleTimeout").getOrElse("600000")
  val maxLifetime          = root.getAs[String]("maxLifetime").getOrElse("1800000")
  val connectionInitSqlOpt = root.getAs[String]("connectionInitSql")
  val hikariConfig = {
    val config = new HikariConfig()
    config.setUsername(userName)
    config.setPassword(password)
    config.setDriverClassName(driver)
    config.setJdbcUrl(url)
    config.addDataSourceProperty("poolName", connectionPoolName)
    config.addDataSourceProperty("maximumPoolSize", maximumPoolSize)
    config.addDataSourceProperty("connectionTimeout", connectionTimeout)
    config.addDataSourceProperty("idleTimeout", idleTimeout)
    config.addDataSourceProperty("maxLifetime", maxLifetime)
    config.addDataSourceProperty("cachePrepStmts", "true")
    config.addDataSourceProperty("prepStmtCacheSize", "250")
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
    connectionInitSqlOpt.foreach(that => config.addDataSourceProperty("connectionInitSql", that))
    config
  }
}

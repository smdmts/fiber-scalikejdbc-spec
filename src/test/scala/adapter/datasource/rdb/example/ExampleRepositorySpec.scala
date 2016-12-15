package adapter.datasource.rdb.example

import adapter.datasource.rdb._
import co.paralleluniverse.fibers.jdbc.FiberConnection
import org.scalatest._

/**
 * ExampleRepositorySpec.
 */
class ExampleRepositorySpec extends fixture.FlatSpec
  with AutoRollbackFeature
  with Matchers {

  "fiber-jdbc" should "access" in { implicit session =>
    val repository = ExampleRepository()
    val writeEntity = Example(None, "value")
    repository.create(writeEntity)
    val readEntity = ExampleRepository().resolveBy(1)
    readEntity.map(_.value) shouldBe Some("value")
    session.connection.isInstanceOf[FiberConnection] shouldBe true
  }

}

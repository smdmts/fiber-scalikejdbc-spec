package adapter.datasource.rdb.example

import scalikejdbc._
import skinny.orm.SkinnyCRUDMapper

/**
 * ExampleRepository.
 */
case class ExampleRepository(implicit session: DBSession) {
  def resolveBy(id: Long): Option[Example] = {
    DBExampleRepository.findById(id)
  }

  def create(entity:Example):Unit = {
    DBExampleRepository.createWithAttributes('value -> entity.value)
  }
}


object DBExampleRepository extends SkinnyCRUDMapper[Example] {
  override val tableName          = "example"
  override val connectionPoolName = 'default

  override def defaultAlias = createAlias("a")
  override def extract(rs: WrappedResultSet, n: ResultName[Example]) = Example(
    id = Some(rs.get[Long](n.id)),
    value = rs.get[String](n.value)
  )
}

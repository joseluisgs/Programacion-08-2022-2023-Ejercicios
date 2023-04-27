package repositorio.base

interface CrudRepository<T, ID> {
    fun findAll(): List<T>
    fun findById(uuid: ID): T?
    fun save(entity: T): T
    fun delete(entity: T): Boolean
    fun deleteById(uuid: ID): Boolean
    fun deleteAll(dropAll: Boolean)
}
package service.repository.base

interface CrudRepository<T, ID> {
    fun findById(id: ID): T?
    fun getAll(): List<T>
    fun save(entity: T): T
    fun delete(id: ID): Boolean
    fun deleteAll()
}
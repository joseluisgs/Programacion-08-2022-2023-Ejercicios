package repositories

interface ICrudRepository<T, ID> {
    fun findById(id: ID): T?
    fun findAll(): List<T>
    fun create(entity: T): T
    fun update(entity: T): T?
    fun delete(entity: T): Boolean
    fun deleteById(id: ID): Boolean
}

package repositorio.base

interface BaseRepository<T, ID> {
    fun findAll(): List<T>
    fun findById(id: ID): T?
    fun save(entity: T): T
    fun delete(entity: T): Boolean
    fun deleteById(id: ID): Boolean
    fun deleteAll()
}
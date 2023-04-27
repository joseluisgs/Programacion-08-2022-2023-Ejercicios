package repositories

import models.Coche

interface CrudRepository<T, ID> {
    fun findAll(): List<T>
    fun findById(id: ID): T?
    fun save(obj: T): T
    fun deleteById(id: ID): Boolean
    fun delete(obj: T): Boolean
    fun update(obj: T): T?
}

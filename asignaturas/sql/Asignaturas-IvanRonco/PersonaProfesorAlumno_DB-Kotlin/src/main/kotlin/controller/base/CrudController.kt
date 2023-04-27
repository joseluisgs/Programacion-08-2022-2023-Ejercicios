package controller.base

import com.github.michaelbull.result.Result

interface CrudController<T, ID, ERROR> {
    fun findAll(): List<T>
    fun findById(id: ID): Result<T, ERROR>
    fun save(entity: T): Result<T, ERROR>
    fun saveAll(entities: List<T>)
    fun delete(entity: T): Result<Boolean, ERROR>
    fun deleteById(id: ID): Result<Boolean, ERROR>
    fun deleteAll()
}
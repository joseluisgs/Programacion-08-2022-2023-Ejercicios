package service.storage.base

interface StorageService<T> {
    fun safeAll(entites: List<T>)
    fun loadAll(): List<T>
}
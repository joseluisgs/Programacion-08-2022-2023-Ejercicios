package services.storage

interface Storage<T> {
    fun save(items: List<T>)
    fun load(): List<T>
}

package storage

interface StorageService<T> {
    fun guardar(items: List<T>)
    fun cargar(): List<T>
}

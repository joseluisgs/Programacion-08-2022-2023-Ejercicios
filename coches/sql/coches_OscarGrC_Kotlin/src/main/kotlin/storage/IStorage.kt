package storage

interface IStorage<T> {
    fun loadData(): List<T>

    fun saveData(data: List<T>): Boolean

}
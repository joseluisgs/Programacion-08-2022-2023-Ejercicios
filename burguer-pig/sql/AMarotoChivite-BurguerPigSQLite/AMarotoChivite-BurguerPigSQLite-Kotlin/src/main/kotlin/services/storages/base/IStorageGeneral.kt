package services.storages.base

interface IStorageGeneral<Model> {
    fun readFile(): List<Model>
    fun writeFile(data: List<Model>): Boolean
}
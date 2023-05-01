package controllers

import models.Coche
import repositories.Coches.CocheRepository
import services.coches.CocheStorageImp

class CocheController(
    private val repository: CocheRepository,
    private val storage: CocheStorageImp
) {

    fun saveInDataBase(item: Coche): Coche {
        repository.saveIntoDataBase(item)
        return item
    }

    fun saveAll(items: List<Coche>): List<Coche> {
        items.forEach { repository.saveIntoDataBase(it) }
        return items
    }

    fun deleteById(id: Long): Boolean {
        return repository.deleteFromDatabaseById(id)
    }

    fun clearTables(): Boolean {
        return repository.clearTables()
    }

    fun saveIntoJson(items: List<Coche>): List<Coche> {
        return storage.saveIntoJson(items)
    }

    fun loadFromCsv(): List<Coche> {
        return storage.loadDataFromCsv()
    }

    fun loadFromJson(): List<Coche> {
        return storage.loadDatafromJson()
    }


}
package controllers

import models.Conductor
import repositories.conductores.ConductorRepository
import services.conductor.ConductorStorageImp

class ConductorController(
    val repository: ConductorRepository,
    val storage : ConductorStorageImp
) {

    fun saveAll(items: List<Conductor>): List<Conductor> {
        items.forEach { repository.saveIntoDataBase(it) }
        return items
    }

    fun clearTables(): Boolean {
        return repository.clearTables()
    }

    fun deleteById(uuid: String): Boolean {
        return repository.deleteFromDatabaseById(uuid)
    }

    fun save(item: Conductor): Conductor {
        return repository.saveIntoDataBase(item)
    }

    fun saveIntoJson(items: List<Conductor>): List<Conductor> {
        return storage.saveIntoJson(items)
    }

    fun loadFromJson(): List<Conductor> {
        return storage.loadDatafromJson()
    }

    fun loadFromCSV(): List<Conductor> {
        return storage.loadDataFromCsv()
    }




}
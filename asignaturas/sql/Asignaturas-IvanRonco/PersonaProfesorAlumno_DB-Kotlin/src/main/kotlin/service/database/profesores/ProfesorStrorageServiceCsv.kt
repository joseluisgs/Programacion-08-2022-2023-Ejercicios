package service.database.profesores

import models.Profesor
import service.storage.persona.PersonaStorageServiceCsv

class ProfesorStrorageServiceCsv: ProfesorStorageService {

    val storage = PersonaStorageServiceCsv("profesores")
    override fun saveAll(entities: List<Profesor>) {
        storage.saveAll(entities)
    }

    override fun loadAll(): List<Profesor> {
        return storage.loadAll().filterIsInstance<Profesor>()
    }
}
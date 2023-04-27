package service.database.profesores

import models.Profesor
import service.storage.persona.PersonaStorageServiceXml

class ProfesorStorageServiceXml: ProfesorStorageService {

    private val storage = PersonaStorageServiceXml("profesores")

    override fun saveAll(entities: List<Profesor>) {
        storage.saveAll(entities)
    }

    override fun loadAll(): List<Profesor> {
        return storage.loadAll().filterIsInstance<Profesor>()
    }
}
package service.database.profesores

import models.Profesor
import service.storage.persona.PersonaStorageServiceJson

@OptIn(ExperimentalStdlibApi::class)
class ProfesorStorageServiceJson: ProfesorStorageService {

    private val storage = PersonaStorageServiceJson("profesores")

    override fun saveAll(entities: List<Profesor>) {
        storage.saveAll(entities)
    }

    override fun loadAll(): List<Profesor> {
        return storage.loadAll().filterIsInstance<Profesor>()
    }
}
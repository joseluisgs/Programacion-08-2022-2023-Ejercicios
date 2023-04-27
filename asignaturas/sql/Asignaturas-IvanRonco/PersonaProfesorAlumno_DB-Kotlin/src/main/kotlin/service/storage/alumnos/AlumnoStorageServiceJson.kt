package service.storage.alumnos

import models.Alumno
import service.storage.persona.PersonaStorageServiceJson

@OptIn(ExperimentalStdlibApi::class)
class AlumnoStorageServiceJson: AlumnoStorageService {

    private val storage = PersonaStorageServiceJson("alumnos")

    override fun saveAll(entities: List<Alumno>) {
        storage.saveAll(entities)
    }

    override fun loadAll(): List<Alumno> {
        return storage.loadAll().filterIsInstance<Alumno>()
    }
}
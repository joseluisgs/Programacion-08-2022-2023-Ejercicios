package service.storage.alumnos

import models.Alumno
import models.Persona
import service.storage.persona.PersonaStorageService
import service.storage.persona.PersonaStorageServiceCsv

class AlumnoStrorageServiceCsv: AlumnoStorageService {

    val storage = PersonaStorageServiceCsv("alumnos")
    override fun saveAll(entities: List<Alumno>) {
        storage.saveAll(entities)
    }

    override fun loadAll(): List<Alumno> {
        return storage.loadAll().filterIsInstance<Alumno>()
    }
}
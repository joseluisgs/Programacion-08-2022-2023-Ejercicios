package service.storage.alumnos

import models.Alumno
import service.storage.persona.PersonaStorageServiceXml

class AlumnoStorageServiceXml: AlumnoStorageService {

    private val storage = PersonaStorageServiceXml("alumnos")

    override fun saveAll(entities: List<Alumno>) {
        storage.saveAll(entities)
    }

    override fun loadAll(): List<Alumno> {
        return storage.loadAll().filterIsInstance<Alumno>()
    }
}
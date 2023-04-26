package repositorio.conductor

import models.conductor.Conductor
import repositorio.base.CrudRepository
import java.util.UUID

interface ConductorRepositoryBase: CrudRepository<Conductor, UUID> {
    fun findByDni(dni: String): Conductor?
    fun findByEdad(edad: Int): List<Conductor>
    fun findByNombre(nombre: String): List<Conductor>
}
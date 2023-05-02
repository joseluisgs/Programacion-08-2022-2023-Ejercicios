package repositories.conductor

import models.Conductor
import repositories.CrudRepository
import java.util.UUID

interface ConductoresRepository: CrudRepository<Conductor, UUID> {
}
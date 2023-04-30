package repositories.coche

import models.Coche
import repositories.CrudRepository

interface CochesRepository: CrudRepository<Coche, Long> {
}
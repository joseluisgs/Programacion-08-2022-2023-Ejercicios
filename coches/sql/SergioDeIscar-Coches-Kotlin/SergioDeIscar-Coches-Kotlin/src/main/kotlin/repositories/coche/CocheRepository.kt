package repositories.coche

import models.Coche
import repositories.CrudRepository

interface CocheRepository: CrudRepository<Coche, Long>
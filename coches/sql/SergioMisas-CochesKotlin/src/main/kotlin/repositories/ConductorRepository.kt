package repositories

import models.Conductor
import java.util.UUID

interface ConductorRepository: CrudRepository<Conductor, UUID>

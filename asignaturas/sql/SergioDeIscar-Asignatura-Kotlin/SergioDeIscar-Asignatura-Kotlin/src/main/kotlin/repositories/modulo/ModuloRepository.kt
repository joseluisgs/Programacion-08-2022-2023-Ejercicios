package repositories.modulo

import models.Modulo
import repositories.CrudRepository
import java.util.UUID

interface ModuloRepository: CrudRepository<Modulo, UUID>
package repositorio.ingredientes

import models.Ingrediente
import repositorio.base.BaseRepository
import java.util.UUID

interface IngredientesRepositoryBase: BaseRepository<Ingrediente, Long> {
    fun findByDisponible(disponible: Boolean): List<Ingrediente>
    fun findByUuid(uuid: UUID): Ingrediente?
    fun findByName(name: String): List<Ingrediente>
}
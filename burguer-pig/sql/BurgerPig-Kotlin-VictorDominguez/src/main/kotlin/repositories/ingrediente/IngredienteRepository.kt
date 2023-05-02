package repositories.ingrediente

import models.Ingrediente
import repositories.CrudRepository

interface IngredienteRepository: CrudRepository<Ingrediente, Long> {
}
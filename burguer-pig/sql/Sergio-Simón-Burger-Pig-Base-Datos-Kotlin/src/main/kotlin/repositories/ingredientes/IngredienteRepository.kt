package repositories.ingredientes

import models.Ingrediente
import repositories.base.CrudRepository

interface IngredienteRepository: CrudRepository<Ingrediente, Long> {

}
package mapper

import appDatabase.IngredienteEntity
import models.Ingrediente
import java.time.LocalDate
import java.util.*

fun IngredienteEntity.toIngrediente(): Ingrediente{
    return Ingrediente(
        this.id,
        UUID.fromString(this.uuid),
        this.nombre,
        this.precio,
        this.cantidad.toInt(),
        LocalDate.parse(this.createdAt),
        LocalDate.parse(this.updatedAt),
        this.disponible == 1L
    )
}
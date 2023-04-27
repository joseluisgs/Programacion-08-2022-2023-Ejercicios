package mappers

import database.BurguerTable
import database.IngredienteTable
import models.Burguer
import models.Ingrediente
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

fun toIngredient(ingrediente: IngredienteTable): Ingrediente {
    var booleanValue = true
    if (ingrediente.avaliable.toInt() == 0){
        booleanValue = false
    }
    return Ingrediente(
        ingrediente.id,
        ingrediente.name.toString(),
        ingrediente.price,
        ingrediente.stock.toInt(),
        LocalDateTime.parse(ingrediente.created_at),
        LocalDateTime.parse(ingrediente.updated_at),
        booleanValue
    )
}
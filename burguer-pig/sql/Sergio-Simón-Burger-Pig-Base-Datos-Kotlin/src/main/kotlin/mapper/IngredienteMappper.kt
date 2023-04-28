package mapper

import dto.IngredientesDto
import dto.ListIngredientesDto
import models.Ingrediente
import java.time.LocalDateTime
import java.util.*

fun Ingrediente.toDto() = IngredientesDto(
    id= this.id,
    uuid= this.uuid.toString(),
    name= this.name,
    price= this.price.toString(),
    disponible= disponible.toString(),
    cantidad= cantidad.toString(),
    createdAt= this.createdAt.toString(),
    updatedAt= this.updatedAt.toString(),
)

fun IngredientesDto.toIngrediente() = Ingrediente(
    id= id.toLong(),
    uuid= UUID.fromString(uuid),
    name= name,
    disponible= disponible.toBoolean(),
    cantidad = cantidad.toInt(),
    price= price.toDouble(),
    createdAt= LocalDateTime.parse(createdAt),
    updatedAt= LocalDateTime.parse(updatedAt)
)

fun List<Ingrediente>.toDto() = ListIngredientesDto(
    listIngredientesDto = map { it.toDto() }
)

fun ListIngredientesDto.toIngredienteList() = listIngredientesDto.map { it.toIngrediente() }
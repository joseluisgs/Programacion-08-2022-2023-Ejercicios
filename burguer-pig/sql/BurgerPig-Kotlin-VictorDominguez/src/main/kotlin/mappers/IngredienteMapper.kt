package mappers

import dto.IngredienteDto
import dto.IngredienteListDto
import models.Ingrediente

fun Ingrediente.toIngredienteDto() = IngredienteDto(
    this.id.toString(),
    this.nombre,
    this.precio.toString()
)

fun IngredienteDto.toIngrediente() = Ingrediente(
    this.id.toLong(),
    this.nombre,
    this.precio.toDouble()
)

fun List<Ingrediente>.toIngredienteListDto() = IngredienteListDto(
    this.map { it.toIngredienteDto() }
)

fun IngredienteListDto.toIngredienteList() = this.ingredientes.map { it.toIngrediente() }
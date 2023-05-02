package mappers

import dto.HamburguesaDto
import dto.HamburguesaListDto
import models.Hamburguesa
import java.util.UUID

fun Hamburguesa.toHamburguesaDto() = HamburguesaDto(
    this.id.toString(),
    this.nombre,
    this.ingredientes.toIngredienteListDto(),
    this.precio.toString()
)

fun HamburguesaDto.toHamburguesa() = Hamburguesa(
    UUID.fromString(this.id),
    this.nombre,
    this.ingredientes.toIngredienteList().toMutableList(),
    this.precio.toDouble()
)

fun List<Hamburguesa>.toHamburguesaListDto() = HamburguesaListDto(
    this.map { it.toHamburguesaDto() }
)

fun HamburguesaListDto.toHamburguesaList() = this.hamburguesas.map { it.toHamburguesa() }
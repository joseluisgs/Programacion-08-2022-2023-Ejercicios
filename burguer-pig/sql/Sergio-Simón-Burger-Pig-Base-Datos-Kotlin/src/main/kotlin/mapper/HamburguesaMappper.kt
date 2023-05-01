package mapper

import dto.HamburguesaDto
import dto.HamburguesasListDto
import models.Hamburguesa
import java.time.LocalDateTime
import java.util.*

fun Hamburguesa.toDto() = HamburguesaDto(
    id= id.toString(),
    uuid= uuid.toString(),
    name= name,
    cantidad= cantidad.toString(),
    lineaHamburguesa= lineaHamburguesa.map { it.toDto() }.toMutableList(),
    createdAt= createdAt.toString(),
    updatedAt= updatedAt.toString(),
    precio = precio.toString(),
)

fun HamburguesaDto.toHamburguesaList() = Hamburguesa(
    id= id.toLong(),
    uuid= UUID.fromString(uuid),
    name= name,
    cantidad= cantidad.toInt(),
    lineaHamburguesa= lineaHamburguesa.map { it.toLineaHamburguesa() }.toMutableList(),
    createdAt= LocalDateTime.parse(createdAt),
    updatedAt= LocalDateTime.parse(updatedAt),
)

fun List<Hamburguesa>.toDto() = HamburguesasListDto(
    listHamburguesas = map { it.toDto() }
)

fun HamburguesasListDto.toHamburguesaList() = listHamburguesas.map { it.toHamburguesaList() }
package mapper

import dto.LineaHamburguesaDto
import dto.LineaHamburguesaListDto
import models.LineaHamburguesa

fun LineaHamburguesa.toDto() = LineaHamburguesaDto(
    lineaId= lineaId.toString(),
    idIngrediente= idIngrediente.toString(),
    idHamburguesa= idHamburguesa.toString(),
    precioIngrediente= precioIngrediente.toString(),
    cantidadIngrediente= cantidadIngrediente.toString(),
    precioTotal= precioTotal.toString(),
)

fun LineaHamburguesaDto.toLineaHamburguesa() = LineaHamburguesa(
    lineaId= lineaId.toLong(),
    idIngrediente= idIngrediente.toLong(),
    idHamburguesa= idHamburguesa.toLong(),
    precioIngrediente= precioIngrediente.toDouble(),
    cantidadIngrediente= cantidadIngrediente.toInt(),
)

fun List<LineaHamburguesa>.toDto() = LineaHamburguesaListDto(
    lineaHamburguesa= map { it.toDto() }
)

fun LineaHamburguesaListDto.toLineaHamburguesa() = lineaHamburguesa.map { it.toLineaHamburguesa() }
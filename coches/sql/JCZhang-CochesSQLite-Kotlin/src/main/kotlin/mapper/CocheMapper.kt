package mapper

import dto.CocheDTO
import dto.CocheListDto
import models.Coche


fun CocheDTO.toCoche() = Coche(
    id = id,
    marca = marca,
    modelo = modelo,
    precio = precio,
    tipoMotor = enumValueOf(tipoMotor)
)

fun Coche.toDto() = CocheDTO(
    id = this.id,
    marca = this.marca,
    modelo = this.modelo,
    precio = this.precio,
    tipoMotor = this.tipoMotor.toString()
)


fun List<Coche>.CocheListToDto() = CocheListDto(coches = map { it.toDto() })

fun CocheListDto.toCocheList() = coches.map { it.toCoche() }
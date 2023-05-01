package mappers

import dto.CocheDto
import models.Coche
import models.TypeMotor
import java.util.UUID

fun Coche.toDto(): CocheDto{
    return CocheDto(
        id.toString(),
        marca,
        modelo,
        precio.toString(),
        motor.toString(),
        idConductor.toString()
    )
}

fun CocheDto.toClass(): Coche{
    return Coche(
        id.toLong(),
        marca,
        modelo,
        precio.toFloat(),
        TypeMotor.valueOf(motor),
        UUID.fromString(idConductor)
    )
}
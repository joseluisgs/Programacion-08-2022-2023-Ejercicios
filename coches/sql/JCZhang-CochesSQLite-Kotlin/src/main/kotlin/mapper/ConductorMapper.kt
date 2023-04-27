package mapper

import dto.ConductorDto
import dto.ConductorListDto
import models.Conductor
import utils.localDateFromString
import java.util.*


fun ConductorDto.toConductor() = Conductor(
    uuid = UUID.fromString(uuid),
    nombre = nombre,
    fechaCarnet = localDateFromString(fechaCarnet)
)

fun Conductor.toDto() = ConductorDto(
    uuid = this.uuid.toString(),
    nombre = this.nombre,
    fechaCarnet = this.fechaCarnet.toString()
)

fun List<Conductor>.toDtoList() = ConductorListDto(conductores = map { it.toDto() })

fun ConductorListDto.toConductorList() = conductores.map { it.toConductor() }
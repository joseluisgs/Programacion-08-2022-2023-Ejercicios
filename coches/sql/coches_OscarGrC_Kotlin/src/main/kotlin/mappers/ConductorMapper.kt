package mappers

import Dto.CocheDTO
import Dto.ConductorDTO
import models.Coche
import models.Conductor
import models.TipoMotor
import java.time.LocalDate
import java.util.*

fun Conductor.toDTO(): ConductorDTO {
    return ConductorDTO(uuid= this.uuid.toString(), nombre = this.nombre, fechaCarnet = this.fechaCarnet.toString())
}

fun ConductorDTO.toCoche(): Conductor {
    return Conductor(uuid = UUID.fromString(this.uuid),
        nombre = this.nombre,fechaCarnet =LocalDate.parse(this.fechaCarnet))
}
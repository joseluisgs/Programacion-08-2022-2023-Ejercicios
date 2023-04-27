package mappers

import Dto.ModuloDTO
import models.Grado
import models.Modulo
import org.simpleframework.xml.Element
import java.time.LocalDate
import java.util.*

fun Modulo.toDTO(): ModuloDTO {
    return ModuloDTO(uuid =this.uuid.toString(), nombre = this.nombre, curso = this.curso, grado = this.grado.name)
}

fun ModuloDTO.toCoche(): Modulo {
    return Modulo(uuid =UUID.fromString(this.uuid), nombre = this.nombre,
        curso = this.curso, grado = Grado.valueOf(this.grado))
}
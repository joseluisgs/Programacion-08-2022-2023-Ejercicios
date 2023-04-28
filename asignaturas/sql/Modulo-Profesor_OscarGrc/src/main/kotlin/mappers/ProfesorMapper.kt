package mappers
import Dto.ProfesorDTO
import models.*
import java.time.LocalDate

fun Profesor.toDTO(): ProfesorDTO {
        return ProfesorDTO(id = this.id.toString(),
            nombre = this.nombre, fechaIncorporacion = this.fehcaIncorpracion.toString())
}

fun ProfesorDTO.toCoche(): Profesor {
        return Profesor(
            id = this.id.toLong(),
            nombre = this.nombre, fehcaIncorpracion = LocalDate.parse(this.fechaIncorporacion))

}
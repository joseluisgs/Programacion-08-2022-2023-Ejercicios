package mappers
import Dto.CocheDTO
import models.*

fun Coche.toDTO(): CocheDTO {
        return CocheDTO(id = this.id.toString(), marca = this.marca, modelo = this.modelo,
            precio = this.precio.toString(),motor=this.motor.name)
}

fun CocheDTO.toCoche(): Coche {
        return Coche(id = this.id.toLong(), marca = this.marca, modelo = this.modelo,
        precio = this.precio.toDouble(), TipoMotor.valueOf(this.motor))

}
package mapper

import database.VehiculoEntity
import models.vehiculo.Vehiculo
import service.database.vehiculos.ConfigDatabase
import java.util.*

fun VehiculoEntity.toVehiculo(): Vehiculo{
    return Vehiculo(
        UUID.fromString(this.uuid),
        this.modelo,
        this.kilometro.toInt(),
        this.anyoMatriculacion.toInt(),
        this.apto == 1L,
        ConfigDatabase.vehiculoQuery.getMotorByUUID(this.motorId).executeAsOne().toMotor()
    )
}
package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.VehicleError
import models.Vehicle

object VehicleValidator {
    fun validate(vehicle: Vehicle): Result<Vehicle, VehicleError> {
        return when {
            vehicle.uuid.toString().isBlank() -> Err(VehicleError.UuidInvalid("EL UUID no puede estar vacío"))
            vehicle.model.isBlank() || vehicle.model.length <= 3 -> Err(VehicleError.ModelInvalid("El modelo no puede estar vacío y menor de 3 letras"))
            vehicle.motor.toString()
                .isBlank() || vehicle.motor == Vehicle.TypeMotor.ERROR -> Err(VehicleError.MotorInvalid("El motor no puede estar vacío o hubo ERROR inesperado"))

            vehicle.foreignUuidConductor.toString()
                .isBlank() -> Err(VehicleError.UuidConductorInvalid("El UUID del conductor asociado es erróneo"))

            else -> Ok(vehicle)
        }
    }
}
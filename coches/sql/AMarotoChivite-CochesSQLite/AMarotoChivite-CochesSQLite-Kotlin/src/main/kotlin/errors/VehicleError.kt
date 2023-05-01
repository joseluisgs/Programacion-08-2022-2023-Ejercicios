package errors

sealed class VehicleError(val message: String) {
    class UuidInvalid(message: String) : VehicleError(message)
    class ModelInvalid(message: String) : VehicleError(message)
    class MotorInvalid(message: String) : VehicleError(message)
    class UuidConductorInvalid(message: String) : VehicleError(message)
    class NotFound(message: String) : VehicleError(message)
}

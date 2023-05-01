package errors

sealed class ConductorError(val message: String) {
    class ConductorNotFound(message: String) : ConductorError("ERROR: Conductor no encontrado: $message")
    class InvalidConductor(message: String) : ConductorError("ERROR: Conductor no válido: $message")
}

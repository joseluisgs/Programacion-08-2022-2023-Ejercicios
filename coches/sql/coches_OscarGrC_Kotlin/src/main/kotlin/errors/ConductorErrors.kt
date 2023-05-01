package errors

sealed class ConductorErrors(val message: String) {
    class FalloAlActualizar(message: String) : ConductorErrors(message)
    class FalloAlSalvar(message: String) : ConductorErrors(message)
    class ConductorNoEncontrado(message: String):ConductorErrors(message)
    class NombreVacio(message: String):ConductorErrors(message)
}
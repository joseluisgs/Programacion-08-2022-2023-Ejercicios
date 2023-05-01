package errors

sealed class ProfesorErrors(val message: String) {
    class FalloAlActualizar(message: String) : ProfesorErrors(message)
    class FalloAlSalvar(message: String) : ProfesorErrors(message)
    class ProfesorNoEncontrado(message: String) : ProfesorErrors(message)
}
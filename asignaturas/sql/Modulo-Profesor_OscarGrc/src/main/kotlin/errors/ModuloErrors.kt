package errors

sealed class ModuloErrors(val message: String) {
    class FalloAlActualizar(message: String) : ModuloErrors(message)
    class FalloAlSalvar(message: String) : ModuloErrors(message)
    class NombreVacio(message: String):ModuloErrors(message)
    class ModuloNoEncontrado(message: String):ModuloErrors(message)
}
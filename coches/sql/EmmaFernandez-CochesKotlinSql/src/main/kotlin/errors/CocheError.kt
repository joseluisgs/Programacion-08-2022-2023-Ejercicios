package errors

sealed class CocheError(message: String) {
    class CocheNotFound(message: String) : CocheError("ERROR: Coche no encontrado: $message")
    class InvalidCoche(message: String) : CocheError("ERROR: Coche no válido: $message")
}

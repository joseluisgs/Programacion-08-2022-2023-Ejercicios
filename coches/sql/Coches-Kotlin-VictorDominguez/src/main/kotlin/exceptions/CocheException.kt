package exceptions

sealed class CocheException(message: String): Exception(message)
class CocheNoEncontradoException(message: String): CocheException("Coche no encontrado: $message")
class CocheNoValidoException(message: String): CocheException("Coche no válido: $message")
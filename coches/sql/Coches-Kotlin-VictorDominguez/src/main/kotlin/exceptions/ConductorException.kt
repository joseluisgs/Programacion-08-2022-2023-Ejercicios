package exceptions

sealed class ConductorException(message: String): Exception(message)
class ConductorNoEncontradoException(message: String): ConductorException("Conductor no encontrado: $message")
class ConductorNoValidoException(message: String): ConductorException("Conductor no valido: $message")
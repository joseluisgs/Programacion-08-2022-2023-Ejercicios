package exceptions

sealed class HamburguesaException(message: String): Exception(message)
class HamburguesaNoEncontradaException(message: String): HamburguesaException("Hamburguesa no encontrada: $message")
class HamburguesaNoValidaException(message: String): HamburguesaException("Hamburguesa no válida: $message")
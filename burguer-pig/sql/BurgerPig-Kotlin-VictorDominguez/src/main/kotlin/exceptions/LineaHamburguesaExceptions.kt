package exceptions

sealed class LineaHamburguesaException(message: String): Exception(message)
class LineaHamburguesaNoEncontradaException(message: String): LineaHamburguesaException("Linea no encontrada: $message")
class LineaHamburguesaNoValidaException(message: String): LineaHamburguesaException("Linea no valida: $message")
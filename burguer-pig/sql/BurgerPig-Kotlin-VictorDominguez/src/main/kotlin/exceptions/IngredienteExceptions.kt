package exceptions

sealed class IngredienteException(message: String): Exception(message)
class IngredienteNoEncontradoException(message: String): IngredienteException("Ingrediente no encontrado: $message")
class IngredienteNoValidoException(message: String): IngredienteException("Ingrediente no válido: $message")
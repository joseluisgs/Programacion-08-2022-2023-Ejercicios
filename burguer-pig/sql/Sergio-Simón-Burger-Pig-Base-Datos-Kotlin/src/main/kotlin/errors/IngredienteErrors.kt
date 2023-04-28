package errors

sealed class IngredienteErrors(message: String): Exception(message) {
    class IngredienteNoEncontrado(message: String) : IngredienteErrors(message)
    class PrecioNoValido(message: String) : IngredienteErrors(message)
    class CantidadNoValida(message: String) : IngredienteErrors(message)
}

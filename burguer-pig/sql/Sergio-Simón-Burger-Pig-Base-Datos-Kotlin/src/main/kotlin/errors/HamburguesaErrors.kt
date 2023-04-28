package errors

sealed class HamburguesaErrors(message: String): Exception(message) {
    class StockInsuficinete(message: String) : HamburguesaErrors(message)
    class PrecioNoValido(message: String) : IngredienteErrors(message)
    class CantidadNoValida(message: String) : IngredienteErrors(message)
}
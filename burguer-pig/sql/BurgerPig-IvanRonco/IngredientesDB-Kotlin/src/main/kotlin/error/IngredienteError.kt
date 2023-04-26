package error

sealed class IngredienteError(val message: String){
    class NombreNoValido(nombre: String): IngredienteError("El nombre: $nombre, no es válido.")
    class PrecioNoValido(precio: String, min: String): IngredienteError("El precio: $precio, no puede ser menor que $min.")
    class CantidadNoValido(cantidad: String, min: String): IngredienteError("La cantidad: $cantidad, no puede ser menor que $min.")
    class IngredienteNotFound(id: String) : IngredienteError("No se encontró al ingrediente de con el identificador: $id")
}
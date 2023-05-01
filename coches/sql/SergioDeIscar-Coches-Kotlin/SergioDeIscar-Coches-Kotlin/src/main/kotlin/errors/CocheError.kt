package errors

sealed class CocheError(val message: String){
    class CocheIdError: CocheError("ERROR: Id no valido")
    class CocheMarcaError: CocheError("ERROR: Marca no valida")
    class CocheModeloError: CocheError("ERROR: Modelo no valida")
    class CochePrecioError: CocheError("ERROR: Precio no valida")

    class CocheNoEncontradoError: CocheError("ERROR: Coche no encontrado")
}
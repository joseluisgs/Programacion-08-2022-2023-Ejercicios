package errors

import com.github.michaelbull.result.Err

sealed class CochesErrors(val message: String) {
    class FalloAlActualizar(message: String) : CochesErrors(message)
    class FalloAlSalvar(message: String) : CochesErrors(message)
    class CocheNoEncontrado(message: String) : CochesErrors(message)
    class MarcaVacia(message: String) : CochesErrors(message)
    class ModeloVacio(message: String) : CochesErrors(message)
    class PrecioNegativo(message: String) : CochesErrors(message)
}
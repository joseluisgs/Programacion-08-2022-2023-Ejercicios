package error

sealed class VehiculoError(val message: String){
    class ModeloNoValido(modelo: String): VehiculoError("""El modelo: "$modelo", no es válido.""")
    class KilometrosNoValidos(kilometros: String, min: String): VehiculoError("Los kilometros: $kilometros, no pueden ser menor que $min.")
    class AñoMatriculacion(añoMatriculacion: String, min: String): VehiculoError("El año de matriculación: $añoMatriculacion, no puede ser menor que $min.")
    class VehiculoNotFound(id: String) : VehiculoError("No se encontró al vehiculo con el identificador: $id")
}
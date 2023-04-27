package error

sealed class MotorError(message: String): VehiculoError(message){
    class ModeloNoValido(modelo: String): MotorError("""El modelo: "$modelo", no es válido.""")
    class CaballosNoValidos(caballos: String, min: String): MotorError("Los caballos: $caballos, no pueden ser menor que $min.")
    class CilindradaNoValida(cilindrada: String, min: String): MotorError("La cilindrada: $cilindrada, no puede ser menor que $min.")
    class PorcentajeCargaNoValida(porcentaje: String, min: String): MotorError("El porcentaje de carga: $porcentaje%, no puede ser menor que $min%.")
    class CapacidadNoValida(capacidad: String, min: String): MotorError("La capacidad: $capacidad, no puede ser menor que $min.")
}
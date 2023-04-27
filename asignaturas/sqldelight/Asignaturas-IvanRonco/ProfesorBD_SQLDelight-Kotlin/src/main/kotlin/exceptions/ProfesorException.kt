package exceptions

sealed class ProfesorException(message: String): RuntimeException(message){
    class NombreNoValido(nombre: String): ProfesorException("""El nombre: "$nombre", no es válido.""")
    class ExperienciasNoValida(precio: String, min: String): ProfesorException("El precio: $precio, no puede ser menor que $min.")
    class ProfesorNotFoundException(id: String) : ProfesorException("No se encontró al profesor con el identificador: $id")
}
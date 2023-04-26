package error

sealed class PersonaError(val message: String){
    class NombreIncorrect(message: String): PersonaError("""El nombre: "$message", no es válido.""")
    class EdadIncorrect(message: String, min: String): PersonaError("""La edad: "$message", no es válida, no puede ser menor que: $min.""")
    class ModuloIncorrect(message: String): PersonaError("""El módulo: "$message", no es válido.""")
    class PersonaNotFound(message: String): PersonaError("La persona con el identificador: $message, no ha sido encontrada.")
}

package error

sealed class ConductorError(val message: String) {
    class DniNoValido(dni: String): ConductorError("""El dni: "$dni", no es válido.""")
    class NombreNoValido(nombre: String): ConductorError("""El nombre: "$nombre", no es válido.""")
    class ApellidosNoValidos(apellidos: String): ConductorError("""Los apellidos: "$apellidos", no son válidos.""")
    class EdadNoValida(edad: String, min: String): ConductorError("La edad: $edad, no puede ser menor que $min.")
    class ConductorNotFound(id: String) : ConductorError("No se encontró al conductor con el identificador: $id")
}
package errors

sealed class ProfesorError(val message: String) {
    class ProfesorIdError: ProfesorError("ERROR: Id no válido del profesor")
    class ProfesorNombreError: ProfesorError("ERROR: Nombre no válido del profesor")
    class ProfesorFechaIncorporacionError: ProfesorError("ERROR: Fecha de incorporación no válida del profesor")

    class ProfesorNoEncontradoError: ProfesorError("ERROR: Profesor no encontrado")
}
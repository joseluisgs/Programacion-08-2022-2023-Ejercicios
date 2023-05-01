package errors

sealed class DocenciaError(val message: String) {
    class IdProfesorInvalid(message: String) : DocenciaError(message)
    class UuidModuloInvalid(message: String) : DocenciaError(message)
    class CursoInvalid(message: String) : DocenciaError(message)
    class GradoInvalid(message: String) : DocenciaError(message)
    class NotFound(message: String) : DocenciaError(message)
}

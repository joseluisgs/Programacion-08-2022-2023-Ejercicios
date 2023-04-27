package errors

sealed class ModuloError(val message: String) {
    class UuidInvalid(message: String) : ModuloError(message)
    class NameInvalid(message: String) : ModuloError(message)
    class CursoInvalid(message: String) : ModuloError(message)
    class GradoInvalid(message: String) : ModuloError(message)
    class NotFound(message: String) : ModuloError(message)
}

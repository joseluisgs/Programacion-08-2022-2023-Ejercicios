package errors

sealed class ConductorError(val message: String) {
    class UuidInvalid(message: String) : ConductorError(message)
    class NameInvalid(message: String) : ConductorError(message)
    class NotFound(message: String) : ConductorError(message)
}

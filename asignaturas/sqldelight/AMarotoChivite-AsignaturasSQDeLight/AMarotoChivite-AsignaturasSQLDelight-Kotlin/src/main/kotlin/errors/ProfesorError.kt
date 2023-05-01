package errors

sealed class ProfesorError(val message: String) {
    class IdInvalid(message: String) : ProfesorError(message)
    class NameInvalid(message: String) : ProfesorError(message)
    class DateInitInvalid(message: String) : ProfesorError(message)
    class NotFound(message: String) : ProfesorError(message)
}

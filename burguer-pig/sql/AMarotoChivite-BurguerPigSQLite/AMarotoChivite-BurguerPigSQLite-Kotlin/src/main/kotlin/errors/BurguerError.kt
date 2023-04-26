package errors

sealed class BurguerError(val message: String) {
    class UuidInvalid(message: String) : BurguerError(message)
    class NameInvalid(message: String) : BurguerError(message)
    class StockInvalid(message: String) : BurguerError(message)
    class NotFound(message: String) : BurguerError(message)
}

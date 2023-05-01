package exceptions

sealed class CochesException(message: String) : Exception(message)
class CsvNotFoundException(message: String) : CochesException(message)

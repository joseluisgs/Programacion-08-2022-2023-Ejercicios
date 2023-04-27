package errors

sealed class ConductorError(val message: String) {
    class ConductorNombreError: ConductorError("ERROR: Nombre no valido")
    class ConductorFechaCarnetError: ConductorError("ERROR: FechaCarnet no valido")
    class ConductorCochesError: ConductorError("ERROR: Algún coche no es valido")

    class ConductorNoEncontradoError: ConductorError("ERROR: Conductor no encontrado")
}
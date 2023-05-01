package errors

sealed class ModuloError(val message: String) {
    class ModuloNombreError: ModuloError("ERROR: Nombre no válido del modulo")
    class ModuloCursoError :ModuloError("Error: Curso no válido del modulo")

    class ModuloNoEncontradoError: ModuloError("ERROR: Modulo no encontrado")
}
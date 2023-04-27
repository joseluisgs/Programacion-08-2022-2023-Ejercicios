package exceptions

import java.io.IOException

sealed class ConductorFileException(message: String): IOException(message) {
    class ConductorFileCantReed(fileType:String): ConductorFileException("ERROR: No se ha podido leer el fichero de tipo: $fileType")
    class ConductorFileCantWrite(fileType:String): ConductorFileException("ERROR: No se ha podido escribir el fichero de tipo: $fileType")
}
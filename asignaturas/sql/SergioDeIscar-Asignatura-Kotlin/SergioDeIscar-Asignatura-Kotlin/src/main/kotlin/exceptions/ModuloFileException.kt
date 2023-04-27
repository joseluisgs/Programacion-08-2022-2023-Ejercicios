package exceptions

import java.io.IOException

sealed class ModuloFileException(message: String): IOException(message) {
    class ModuloFileCantReed(fileType: String): ModuloFileException("ERROR: No se ha podido leer el fichero del tipo: $fileType")
    class ModuloFileCantWrite(fileType: String): ModuloFileException("ERROR: No se ha podido escribir el fichero del tipo: $fileType")
}
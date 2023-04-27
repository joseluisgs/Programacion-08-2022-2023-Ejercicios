package exceptions

import java.io.IOException

sealed class CocheFileException(message: String): IOException(message) {
    class CocheFileCantReed(fileType:String): CocheFileException("ERROR: No se ha podido leer el fichero del tipo $fileType")
    class CocheFileCantWrite(fileType:String): CocheFileException("ERROR: No se ha podido escribir el fichero del tipo $fileType")
}
package exceptions

import java.io.IOException

sealed class ProfesorFileException(message: String): IOException(message){
    class ProfesorFileCantReed(fileType: String): ProfesorFileException("ERROR: No se ha podido leer el fichero del tipo: $fileType")
    class ProfesorFileCantWrite(fileType: String): ProfesorFileException("ERROR: No se ha podido escribir en el fichero del tipo: $fileType")
}
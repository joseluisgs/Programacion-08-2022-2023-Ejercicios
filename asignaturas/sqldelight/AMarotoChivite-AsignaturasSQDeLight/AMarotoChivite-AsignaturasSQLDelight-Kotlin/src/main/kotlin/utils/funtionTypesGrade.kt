package utils

import models.Modulo

fun typeGrade(grado:String): Modulo.TypeGrado {

    when (grado) {
        "DAM" -> {
            return Modulo.TypeGrado.DAM
        }
        "DAW" -> {
            return  Modulo.TypeGrado.DAW
        }
        "ASIR" -> {
            return Modulo.TypeGrado.ASIR
        }
        "SMR" -> {
            return  Modulo.TypeGrado.SMR
        }
        else -> {
            return Modulo.TypeGrado.NONE
        }
    }
}
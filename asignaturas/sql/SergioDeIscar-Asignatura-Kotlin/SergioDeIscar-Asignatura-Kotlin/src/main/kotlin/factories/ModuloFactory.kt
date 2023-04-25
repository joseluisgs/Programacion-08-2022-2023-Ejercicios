package factories

import models.Grado
import models.Modulo
import java.util.*

object ModuloFactory {
    fun getModulosDefault() = listOf(
        Modulo(
            UUID.fromString("ab169d2f-8f46-4bf3-b327-49f372f3e55d"),
            "Programación",
            1,
            Grado.DAM
        ),
        Modulo(
            UUID.fromString("8a23bf34-8132-4f3f-b029-3391b2b33db5"),
            "Sistemas Informáticos",
            2,
            Grado.SMR
        ),
        Modulo(
            UUID.fromString("d520fa71-467e-454a-b2e0-d895e4d8b75c"),
            "Bases de Datos",
            2,
            Grado.DAW
        )
    )
}
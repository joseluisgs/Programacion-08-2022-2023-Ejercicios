package models

import java.util.UUID

data class Modulo (
    val uuid: UUID = UUID.randomUUID(),
    val name: String,
    val curso: Int,
    val grado: TypeGrado
) {
    enum class TypeGrado {
        DAM,
        DAW,
        ASIR,
        SMR,
        NONE
    }
}
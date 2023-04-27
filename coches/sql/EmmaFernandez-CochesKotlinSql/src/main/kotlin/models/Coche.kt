package models

import locale.toLocalMoney

data class Coche(
    val id: Long = 0,
    val marca: String,
    val modelo: String,
    val precio: Double,
    val tipoMotor: TipoMotor,
    val conductor: Conductor
) {
    enum class TipoMotor {
        GASOLINA,
        DIESEL,
        HIBRIDO,
        ELECTRICO
    }

    fun toLocalString(): String =
        "Coche(id=$id, marca=$marca, modelo=$modelo, precio=${precio.toLocalMoney()}, tipoMotor=$tipoMotor)"
}

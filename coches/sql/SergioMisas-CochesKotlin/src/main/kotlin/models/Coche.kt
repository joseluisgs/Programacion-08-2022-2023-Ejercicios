package models

data class Coche(
    val id: Long = 0L,
    val marca: String,
    val modelo: String,
    val precio: Double,
    val motor: TipoMotor,
    val conductor: Conductor
) {
    enum class TipoMotor {
        GASOLINA, DIESEL, HIBRIDO, ELECTRICO
    }
}

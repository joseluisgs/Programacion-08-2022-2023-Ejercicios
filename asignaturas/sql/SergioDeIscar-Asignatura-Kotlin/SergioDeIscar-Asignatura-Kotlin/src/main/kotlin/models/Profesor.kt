package models

import java.time.LocalDate

data class Profesor(
    val id: Long = 0,
    val nombre: String,
    val fechaIncorporacion: LocalDate,
    val modulos: List<Modulo>
){
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Profesor) return false
        return this.id == other.id &&
                this.nombre == other.nombre &&
                this.fechaIncorporacion == other.fechaIncorporacion
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + nombre.hashCode()
        result = 31 * result + fechaIncorporacion.hashCode()
        return result
    }
}
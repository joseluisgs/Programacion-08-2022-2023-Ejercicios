package models

class Profesor(
    id: Long = 0,
    nombre: String,
    val modulo: String
) : Persona(id, nombre){
    override fun toString(): String {
        return "Profesor(id='$id', nombre='$nombre', modulo='$modulo')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Profesor

        if (nombre != other.nombre) return false
        if (modulo != other.modulo) return false

        return true
    }
}
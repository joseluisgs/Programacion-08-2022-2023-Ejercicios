package models

class Alumno(
    id: Long = 0,
    nombre: String,
    val edad: Int
): Persona(id, nombre) {
    override fun toString(): String {
        return "Alumno(id='$id', nombre='$nombre', edad='$edad')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Alumno

        if (nombre != other.nombre) return false
        if (edad != other.edad) return false

        return true
    }
}
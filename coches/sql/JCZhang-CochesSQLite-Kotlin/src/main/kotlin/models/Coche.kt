package models

class Coche(val id: Long, val marca: String, val modelo: String, val precio: Double, val tipoMotor: TipoMotor) {

    override fun toString(): String {
        return "Coche(id=$id, marca='$marca', modelo='$modelo', precio=$precio, tipoMotor=$tipoMotor)"
    }
}

enum class TipoMotor{
    GASOLINA, DIESEL, HIBRIDO, ELECTRICO
}
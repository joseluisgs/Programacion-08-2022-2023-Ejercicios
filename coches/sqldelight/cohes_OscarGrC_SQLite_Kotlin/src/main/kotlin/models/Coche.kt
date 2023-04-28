package models

import java.util.*

data class Coche(
    val id:Long = 0,
    val marca:String,
    val modelo:String,
    val precio:Double,
    val motor:TipoMotor
) {
    override fun toString(): String {
        return "Coche id=$id soy $marca modelo $modelo,tengo motor $motor y mi precio es $precio \n"
    }
}
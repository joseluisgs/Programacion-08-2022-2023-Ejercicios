package models.motor

import java.util.UUID

abstract class Motor(
    val uuid: UUID,
    val modelo: String,
    val caballos: Int
){
    override fun toString(): String {
        return "Motor(" +
                "uuid=$uuid, " +
                "modelo='$modelo', " +
                "caballos=$caballos" +
                ")"
    }
}
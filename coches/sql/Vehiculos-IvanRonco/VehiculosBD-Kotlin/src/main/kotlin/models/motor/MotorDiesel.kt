package models.motor

import java.util.*

class MotorDiesel(
    uuid: UUID = UUID.randomUUID(),
    modelo: String,
    caballos: Int,
    val cilindradaDiesel: Int
): Motor(uuid, modelo, caballos){
    override fun toString(): String {
        return "MotorDiesel(" +
                "uuid=$uuid, " +
                "modelo='$modelo', " +
                "caballos=$caballos, " +
                "cilindradaDiesel=$cilindradaDiesel" +
                ")"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MotorDiesel

        if (uuid != other.uuid) return false
        if (modelo != other.modelo) return false
        if (caballos != other.caballos) return false
        if (cilindradaDiesel != other.cilindradaDiesel) return false

        return true
    }
}
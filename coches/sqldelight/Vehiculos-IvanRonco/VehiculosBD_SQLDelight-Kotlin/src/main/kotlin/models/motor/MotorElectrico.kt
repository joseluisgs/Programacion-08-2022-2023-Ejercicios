package models.motor

import java.util.*

class MotorElectrico(
    uuid: UUID = UUID.randomUUID(),
    modelo: String,
    caballos: Int,
    val porcentajeCargado: Double
): Motor(uuid, modelo, caballos){
    override fun toString(): String {
        return "MotorElectrico(" +
                "uuid=$uuid, " +
                "modelo='$modelo', " +
                "caballos=$caballos, " +
                "porcentajeCargado=$porcentajeCargado" +
                ")"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MotorElectrico

        if (uuid != other.uuid) return false
        if (modelo != other.modelo) return false
        if (caballos != other.caballos) return false
        if (porcentajeCargado != other.porcentajeCargado) return false

        return true
    }
}
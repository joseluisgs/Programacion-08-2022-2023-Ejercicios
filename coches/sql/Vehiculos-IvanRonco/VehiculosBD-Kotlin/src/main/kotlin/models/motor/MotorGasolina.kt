package models.motor

import java.util.*

class MotorGasolina(
    uuid: UUID = UUID.randomUUID(),
    modelo: String,
    caballos: Int,
    val cilindradaGasolina: Int
): Motor(uuid, modelo, caballos){
    override fun toString(): String {
        return "MotorGasolina(" +
                "uuid=$uuid, " +
                "modelo='$modelo', " +
                "caballos=$caballos, " +
                "cilindradaGasolina=$cilindradaGasolina" +
                ")"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MotorGasolina

        if (uuid != other.uuid) return false
        if (modelo != other.modelo) return false
        if (caballos != other.caballos) return false
        if (cilindradaGasolina != other.cilindradaGasolina) return false

        return true
    }
}
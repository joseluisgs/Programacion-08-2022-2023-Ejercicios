package models.motor

import java.util.*

class MotorHibrido(
    uuid: UUID = UUID.randomUUID(),
    modelo: String,
    caballos: Int,
    val capacidadGasolina: Double,
    val capacidadElectrica: Double
): Motor(uuid, modelo, caballos){
    override fun toString(): String {
        return "MotorHibrido(" +
                "uuid=$uuid, " +
                "modelo='$modelo', " +
                "caballos=$caballos, " +
                "capacidadGasolina=$capacidadGasolina, " +
                "capacidadElectrica=$capacidadElectrica" +
                ")"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MotorHibrido

        if (uuid != other.uuid) return false
        if (modelo != other.modelo) return false
        if (caballos != other.caballos) return false
        if (capacidadGasolina != other.capacidadGasolina) return false
        if (capacidadElectrica != other.capacidadElectrica) return false

        return true
    }
}
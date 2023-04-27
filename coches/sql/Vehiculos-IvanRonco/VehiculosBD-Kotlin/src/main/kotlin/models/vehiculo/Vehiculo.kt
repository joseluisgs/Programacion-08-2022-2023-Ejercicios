package models.vehiculo

import models.conductor.Conductor
import models.motor.Motor
import java.util.*

data class Vehiculo(
    val uuid: UUID,
    val modelo: String,
    val kilometros: Int,
    val añoMatriculacion: Int,
    val apto: Boolean,
    val motor: Motor,
    val conductorId: UUID
){
    override fun toString(): String {
        return "Vehiculo("+
                "uuid=$uuid," +
                "modelo='$modelo', " +
                "kilometros=$kilometros, " +
                "añoMatriculacion=$añoMatriculacion, " +
                "apto=pto, " +
                "motor=$motor," +
                "conductorId=$conductorId)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vehiculo

        if (uuid != other.uuid) return false
        if (modelo != other.modelo) return false
        if (kilometros != other.kilometros) return false
        if (añoMatriculacion != other.añoMatriculacion) return false
        if (apto != other.apto) return false
        if (motor != other.motor) return false
        if (conductorId != other.conductorId) return false

        return true
    }
}
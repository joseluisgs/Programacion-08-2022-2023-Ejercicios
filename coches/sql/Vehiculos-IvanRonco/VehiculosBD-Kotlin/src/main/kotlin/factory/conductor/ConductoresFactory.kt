package factory.conductor

import factory.vehiculo.VehiculosFactory
import models.conductor.Conductor
import java.util.*

object ConductoresFactory {
    fun createSomoConductores(): List<Conductor> {
        val conductores = mutableListOf<Conductor>()
        repeat((1..3).random()){
            val conductorId = UUID.randomUUID()
            conductores.add(
                Conductor(
                    conductorId,
                    arrayOf("54637869A", "87465038S", "65937059P", "54758209F", "76584987F", "34652876K", "54098763Ñ", "84987033J").random(),
                    arrayOf("Manuel", "Ramón", "Cajal", "Nefer").random(),
                    arrayOf("Ronco", "Oliva", "Manzano", "Marmol").random(),
                    (18..45).random(),
                    VehiculosFactory.createSome(conductorId)
                )
            )
        }
        return conductores
    }
}
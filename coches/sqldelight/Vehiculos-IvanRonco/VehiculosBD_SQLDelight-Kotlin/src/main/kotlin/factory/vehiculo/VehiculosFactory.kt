package factory.vehiculo

import factory.motor.MotoresFactory
import models.vehiculo.Vehiculo
import java.time.LocalDate
import java.util.UUID

object VehiculosFactory {
    fun createSome(): List<Vehiculo>{
        val vehiculos = mutableListOf<Vehiculo>()
        val modelos = listOf<String>("Peugeot", "Toyota", "Citroen", "Alfa Romeo", "Renault")
        repeat((1..5).random()) {
            vehiculos.add(
                Vehiculo(
                    uuid = UUID.randomUUID(),
                    modelo = modelos.random(),
                    kilometros = (0..5000).random(),
                    añoMatriculacion = LocalDate.now().year,
                    apto = (1..2).random() == 1,
                    motor = MotoresFactory.getMotorRandom()
                )
            )
        }
        return vehiculos
    }
}
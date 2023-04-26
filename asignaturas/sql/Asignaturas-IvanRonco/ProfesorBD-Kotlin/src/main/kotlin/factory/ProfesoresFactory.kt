package factory

import models.Profesor
import java.util.UUID

object ProfesoresFactory {
    fun createSome(): List<Profesor>{
        val preofesores = mutableListOf<Profesor>()
        val nombres = listOf<String>("Romeo", "Ramón", "Gabriel", "BobbyExperience", "Iván")
        repeat((1..5).random()) {
            preofesores.add(
                Profesor(
                    uuid = UUID.randomUUID(),
                    nombre = nombres.random(),
                    experiencia = (1..37).random()
                )
            )
        }
        return preofesores
    }
}
import controller.alumno.AlumnoControllerImpl
import controller.profesor.ProfesorControllerImpl
import factory.PersonaFactory
import models.Alumno
import models.Profesor
import repository.alumnos.AlumnoRepositoryImpl
import repository.profesores.ProfesorRepositoryImpl
import service.database.ConfigDatabase
import service.database.profesores.ProfesorStorageServiceJson
import service.database.profesores.ProfesorStorageServiceXml
import service.database.profesores.ProfesorStrorageServiceCsv
import service.storage.alumnos.AlumnoStorageServiceJson
import service.storage.alumnos.AlumnoStorageServiceXml
import service.storage.alumnos.AlumnoStrorageServiceCsv

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    val personas = PersonaFactory.createPersonasRandom(20)

    val alumnos = personas.filterIsInstance<Alumno>()

    alumnos.forEach { println(it) }

    println()

    val profesores = personas.filterIsInstance<Profesor>()

    profesores.forEach { println(it)  }

    println()

    val profesorController = ProfesorControllerImpl(
        ProfesorRepositoryImpl(
            ConfigDatabase.queries
        ),
        ProfesorStorageServiceJson()
    )

    println("Profesores:")

    profesorController.saveAll(profesores)

    profesorController.export()

    profesorController.findAll().forEach { println(it) }

    println()

    val alumnoController = AlumnoControllerImpl(
        AlumnoRepositoryImpl(
            ConfigDatabase.queries
        ),
        AlumnoStorageServiceJson()
    )

    println("Alumnos:")

    alumnoController.saveAll(alumnos)

    alumnoController.export()

    alumnoController.findAll().forEach { println(it) }

    println()

}
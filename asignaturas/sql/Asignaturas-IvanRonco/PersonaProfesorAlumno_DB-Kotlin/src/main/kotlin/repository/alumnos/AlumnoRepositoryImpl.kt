package repository.alumnos

import database.GeneralDatabaseQueries
import mapper.forSQLDelight.toAlumno
import models.Alumno
import mu.KotlinLogging

class AlumnoRepositoryImpl(
    private val queries: GeneralDatabaseQueries
): AlumnoRepositoryBase {

    private val logger = KotlinLogging.logger {  }

    override fun findByEdad(edad: Int): List<Alumno> {
        logger.debug { "Conseguimos a todos los alumnos, según la edad: $edad" }

        return findAll().filter { it.edad == edad }
    }

    override fun findAll(): List<Alumno> {
        logger.debug { "Conseguimos a todos los alumnos" }

        return queries.getAllAlumnos().executeAsList().map { it.toAlumno() }
    }

    override fun findById(id: Long): Alumno? {
        logger.debug { "Conseguimos al alumno con id: $id" }

        return queries.getAlumnoById(id).executeAsOneOrNull()?.toAlumno()
    }

    override fun save(entity: Alumno): Alumno {
        logger.debug { "Guardo/Actualizo un alumno" }

        return findById(entity.id)?.let {
            update(entity)
        }?: run {
            create(entity)
        }
    }

    private fun create(entity: Alumno): Alumno{
        logger.debug { "Almacenamos un nuevo alumno en la DB" }

        queries.transaction {
            queries.createAlumno(
                entity.nombre,
                entity.edad.toLong()
            )
        }
        return queries.getLastInsertedAlumno().executeAsOne().toAlumno()
    }

    private fun update(entity: Alumno): Alumno{
        logger.debug { "Actualizamos un alumno ya existente en la DB" }

        queries.updateAlumno(
            entity.nombre,
            entity.edad.toLong(),
            entity.id
        )
        return entity
    }

    override fun delete(entity: Alumno): Boolean {
        logger.debug { "Borramos un alumno" }

        return deleteById(entity.id)
    }

    override fun deleteById(id: Long): Boolean {
        logger.debug { "Borramos el alumno con el id: $id" }

        findById(id)?.let {
            queries.deleteAlumnoById(id)
        } ?: run {
            return false
        }
        return true
    }

    override fun deleteAll() {
        logger.debug { "Borramos todos los alumno" }

        queries.deleteAllAlumnos()
    }
}
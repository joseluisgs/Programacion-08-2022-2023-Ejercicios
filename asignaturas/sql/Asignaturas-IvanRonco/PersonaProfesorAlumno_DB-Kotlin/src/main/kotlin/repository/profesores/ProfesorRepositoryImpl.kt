package repository.profesores

import database.GeneralDatabaseQueries
import mapper.forSQLDelight.toProfesor
import models.Profesor
import mu.KotlinLogging

class ProfesorRepositoryImpl(
    val queries: GeneralDatabaseQueries
): ProfesorRepositoryBase {

    private val logger = KotlinLogging.logger {  }

    override fun findByModulo(modulo: String): List<Profesor> {
        logger.debug { "Conseguimos a todos los profesores, según el módulo: $modulo" }

        return findAll().filter { it.modulo == modulo }
    }

    override fun findAll(): List<Profesor> {
        logger.debug { "Conseguimos a todos los profesores" }

        return queries.getAllProfesores().executeAsList().map { it.toProfesor() }
    }

    override fun findById(id: Long): Profesor? {
        logger.debug { "Conseguimos al profesor con id: $id" }

        return queries.getProfesorById(id).executeAsOneOrNull()?.toProfesor()
    }

    override fun save(entity: Profesor): Profesor {
        logger.debug { "Guardo/Actualizo un profesor" }

        return findById(entity.id)?.let {
            update(entity)
        }?: run {
            create(entity)
        }
    }

    private fun create(entity: Profesor): Profesor {
        logger.debug { "Almacenamos un nuevo profesor en la DB" }

        queries.transaction {
            queries.createProfesor(
                entity.nombre,
                entity.modulo
            )
        }
        return queries.getLastInsertedProfesor().executeAsOne().toProfesor()
    }

    private fun update(entity: Profesor): Profesor {
        logger.debug { "Actualizamos un profesor ya existente en la DB" }

        queries.updateProfesor(
            entity.nombre,
            entity.modulo,
            entity.id
        )
        return entity
    }

    override fun delete(entity: Profesor): Boolean {
        logger.debug { "Borramos un profesor" }

        return deleteById(entity.id)
    }

    override fun deleteById(id: Long): Boolean {
        logger.debug { "Borramos el profesor con el id: $id" }

        findById(id)?.let {
            queries.deleteProfesorById(id)
        } ?: run {
            return false
        }
        return true
    }

    override fun deleteAll() {
        logger.debug { "Borramos todos los profesores" }

        queries.deleteAllProfesores()
    }
}
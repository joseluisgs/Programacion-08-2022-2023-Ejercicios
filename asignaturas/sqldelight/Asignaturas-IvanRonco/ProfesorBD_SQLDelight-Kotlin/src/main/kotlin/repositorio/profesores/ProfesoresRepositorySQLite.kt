package repositorio.profesores

import mapper.toProfesorFromEntity
import models.Profesor
import mu.KotlinLogging
import service.database.profesores.ProfesoresDatabase
import java.sql.Statement
import java.time.LocalDate
import java.util.*

object ProfesoresRepositorySQLite: ProfesoresRepositoryBase {

    val db = ProfesoresDatabase

    private val logger = KotlinLogging.logger {  }

    override fun findByExperiencia(experiencia: Int): List<Profesor> {
        logger.debug { "Buscamos segun los años de experiencia: $experiencia" }
        return findAll().filter { it.experiencia == experiencia }
    }

    override fun findByUuid(uuid: UUID): Profesor? {
        logger.debug { "Buscamos segun uuid: $uuid" }

        return findAll().firstOrNull { it.uuid == uuid }
    }

    override fun findByName(name: String): List<Profesor> {
        logger.debug { "Buscamos segun name: $name" }

        return findAll().filter { it.nombre.lowercase().contains(name.lowercase()) }
    }

    override fun findAll(): List<Profesor> {
        logger.debug { "Buscamos todos los profesores" }

        return db.profesoresQueries.getAllProfesores().executeAsList().map { it.toProfesorFromEntity() }
    }

    override fun findById(id: Long): Profesor? {
        logger.debug { "Buscamos segun id: $id" }

        return findAll().firstOrNull { it.id == id }
    }

    override fun save(entity: Profesor): Profesor {
        logger.debug { "Guardamos/actualizamos un profesor" }

        return findAll().firstOrNull{it.id == entity.id}?.let {
            update(entity)
        }?: run {
            create(entity)
        }
    }

    private fun create(entity: Profesor): Profesor {
        logger.debug { "Se inserta un nuevo profesor en la base de datos" }

        db.profesoresQueries.transaction {
            db.profesoresQueries.createProfesor(
                uuid = entity.uuid.toString(),
                nombre = entity.nombre,
                experiencia = entity.experiencia.toLong()
            )
        }
        return db.profesoresQueries.getLastInsertedProfesor().executeAsOne().toProfesorFromEntity()
    }

    private fun update(entity: Profesor): Profesor {
        logger.debug { "Se actualiza un profesor de la base de datos" }

        db.profesoresQueries.updateProfesor(
            nombre = entity.nombre,
            experiencia = entity.experiencia.toLong(),
            id = entity.id
        )
        return findById(entity.id)!!
    }

    override fun delete(entity: Profesor): Boolean {
        logger.debug { "Eliminamos un profesor con el id: ${entity.id}" }
        return deleteById(entity.id)
    }

    override fun deleteById(id: Long): Boolean {
        logger.debug { "Eliminamos un profesor según su id: $id" }

        db.profesoresQueries.deleteProfesor(id)
        return true
    }

    override fun deleteAll() {
        logger.debug { "Eliminamos todos los profesores" }

        db.profesoresQueries.deleteAll()
    }
}
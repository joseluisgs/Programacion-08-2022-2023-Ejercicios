package repositorio.profesores

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
        val profesores = mutableListOf<Profesor>()
        db.connection.use {
            it.prepareStatement("SELECT * FROM PROFESORES").use { stm ->
                val result = stm.executeQuery()
                while(result.next()){
                    profesores.add(
                        Profesor(
                            result.getLong("ID"),
                            UUID.fromString(result.getString("UUID")),
                            result.getString("NOMBRE"),
                            result.getInt("EXPERIENCIA")
                        )
                    )
                }
            }
        }
        return profesores
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
        var myId = 0L
        val sql = """INSERT INTO PROFESORES VALUES (null, ?, ?, ?)"""
        db.connection.use {
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->
                stm.setString(1, entity.uuid.toString())
                stm.setString(2, entity.nombre)
                stm.setInt(3, entity.experiencia)

                stm.executeUpdate()

                val id = stm.generatedKeys
                while(id.next()){
                    myId = id.getLong(1)
                }
            }
        }
        return entity.copy(id = myId)
    }

    private fun update(entity: Profesor): Profesor {
        logger.debug { "Se actualiza un profesor de la base de datos" }

        val sql = "UPDATE PROFESORES SET NOMBRE = ?, EXPERIENCIA = ? WHERE ID = ?"
        db.connection.use {
            it.prepareStatement(sql).use {stm ->
                stm.setString(1, entity.nombre)
                stm.setInt(2, entity.experiencia)
                stm.setLong(3, entity.id)

                stm.executeUpdate()
            }
        }
        return entity
    }

    override fun delete(entity: Profesor): Boolean {
        logger.debug { "Eliminamos un profesor con el id: ${entity.id}" }
        return deleteById(entity.id)
    }

    override fun deleteById(id: Long): Boolean {
        logger.debug { "Eliminamos un profesor según su id: $id" }
        var result = 0
        val sql = "DELETE FROM PROFESORES WHERE ID = ?"
        db.connection.use {
            it.prepareStatement(sql).use { stm ->
                stm.setLong(1, id)

                result = stm.executeUpdate()
            }
        }
        return result > 0
    }

    override fun deleteAll() {
        logger.debug { "Eliminamos todos los profesores" }
        var result = 0
        val sql = "DELETE FROM PROFESORES"
        db.connection.use {
            it.prepareStatement(sql).use { stm ->
                result = stm.executeUpdate()
            }
        }
    }
}
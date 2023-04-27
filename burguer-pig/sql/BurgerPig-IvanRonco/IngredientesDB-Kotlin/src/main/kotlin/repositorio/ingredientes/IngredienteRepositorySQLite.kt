package repositorio.ingredientes

import models.Ingrediente
import mu.KotlinLogging
import service.database.ingredientes.IngredientesDatabase
import java.sql.Statement
import java.time.LocalDate
import java.util.*

object IngredienteRepositorySQLite: IngredientesRepositoryBase {

    val db = IngredientesDatabase

    private val logger = KotlinLogging.logger {  }

    override fun findByDisponible(disponible: Boolean): List<Ingrediente> {
        logger.debug { "Buscamos segun disponibilidad: $disponible" }
        return findAll().filter { it.disponible == disponible }
    }

    override fun findByUuid(uuid: UUID): Ingrediente? {
        logger.info { "Buscamos segun uuid: $uuid" }
        return findAll().firstOrNull { it.uuid == uuid }
    }

    override fun findByName(name: String): List<Ingrediente> {
        logger.info { "Buscamos segun name: $name" }
        return findAll().filter { it.nombre.lowercase().contains(name.lowercase()) }
    }

    override fun findAll(): List<Ingrediente> {
        logger.info { "Buscamos todos los ingredientes" }
        val ingredientes = mutableListOf<Ingrediente>()
        db.connection.use {
            it.prepareStatement("SELECT * FROM INGREDIENTES").use { stm ->
                val result = stm.executeQuery()
                while(result.next()){
                    ingredientes.add(
                        Ingrediente(
                            result.getLong("ID"),
                            UUID.fromString(result.getString("UUID")),
                            result.getString("NOMBRE"),
                            result.getDouble("PRECIO"),
                            result.getInt("CANTIDAD"),
                            LocalDate.parse(result.getString("CREATED_AT")),
                            LocalDate.parse(result.getString("UPDATED_AT")),
                            result.getInt("DISPONIBLE") == 1
                        )
                    )
                }
            }
        }
        return ingredientes
    }

    override fun findById(id: Long): Ingrediente? {
        logger.info { "Buscamos segun id: $id" }
        return findAll().firstOrNull { it.id == id }
    }

    override fun save(entity: Ingrediente): Ingrediente {
        logger.info { "Guardamos/actualizamos un ingrediente" }
        return findAll().firstOrNull{it.id == entity.id}?.let {
            update(entity)
        }?: run {
            create(entity)
        }
    }

    private fun create(entity: Ingrediente): Ingrediente {
        logger.debug { "Se inserta un nuevo ingrediente en la base de datos" }
        val timeNow = LocalDate.now()
        var myId = 0L
        val sql = """INSERT INTO INGREDIENTES VALUES (null, ?, ?, ?, ?, ?, ?, ?)"""
        db.connection.use {
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->
                stm.setString(1, entity.uuid.toString())
                stm.setString(2, entity.nombre)
                stm.setDouble(3, entity.precio)
                stm.setInt(4, entity.cantidad)
                stm.setString(5, timeNow.toString())
                stm.setString(6, timeNow.toString())
                stm.setInt(7, if(entity.disponible) 1 else 0)

                stm.executeUpdate()

                val id = stm.generatedKeys
                while(id.next()){
                    myId = id.getLong(1)
                }
            }
        }
        return entity.copy(id = myId, createAt = timeNow, updatedAt = timeNow)
    }

    private fun update(entity: Ingrediente): Ingrediente {
        logger.debug { "Se actualiza un ingrediente de la base de datos" }
        val timeNow = LocalDate.now()

        val sql = "UPDATE INGREDIENTES SET NOMBRE = ?, PRECIO = ?, CANTIDAD = ?, DISPONIBLE = ?, UPDATED_AT = ? WHERE ID = ?"
        db.connection.use {
            it.prepareStatement(sql).use {stm ->
                stm.setString(1, entity.nombre)
                stm.setDouble(2, entity.precio)
                stm.setInt(3, entity.cantidad)
                stm.setInt(4, if(entity.disponible) 1 else 0)
                stm.setString(5, timeNow.toString())
                stm.setLong(6, entity.id)
                stm.executeUpdate()
            }
        }
        return entity.copy(updatedAt = timeNow)
    }

    override fun delete(entity: Ingrediente): Boolean {
        logger.info { "Eliminamos un ingrediente" }
        return deleteById(entity.id)
    }

    override fun deleteById(id: Long): Boolean {
        logger.info { "Eliminamos un ingrediente según su id: $id" }
        var result = 0
        val sql = "DELETE FROM INGREDIENTES WHERE ID = ?"
        db.connection.use {
            it.prepareStatement(sql).use { stm ->
                stm.setLong(1, id)

                result = stm.executeUpdate()
            }
        }
        return result > 0
    }

    override fun deleteAll() {
        logger.info { "Eliminamos todos los ingredientes" }
        var result = 0
        val sql = "DELETE FROM INGREDIENTES"
        db.connection.use {
            it.prepareStatement(sql).use { stm ->
                result = stm.executeUpdate()
            }
        }
    }
}
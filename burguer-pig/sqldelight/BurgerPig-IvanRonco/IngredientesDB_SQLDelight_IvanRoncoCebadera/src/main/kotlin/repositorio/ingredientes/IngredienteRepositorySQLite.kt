package repositorio.ingredientes

import mapper.toIngrediente
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
        return db.ingredientesQueries.findAllIngredientes().executeAsList().map{ it.toIngrediente() }
    }

    override fun findById(id: Long): Ingrediente? {
        logger.info { "Buscamos segun id: $id" }
        return db.ingredientesQueries.getIngredienteById(id = id).executeAsOneOrNull()?.toIngrediente()
    }

    override fun save(entity: Ingrediente): Ingrediente {
        logger.info { "Guardamos/actualizamos un ingrediente" }
        return findById(id = entity.id)?.let {
            update(entity)
        }?: run {
            create(entity)
        }
    }

    private fun create(entity: Ingrediente): Ingrediente {
        logger.debug { "Se inserta un nuevo ingrediente en la base de datos" }
        val timeNow = LocalDate.now()
        db.ingredientesQueries.transaction {
            db.ingredientesQueries.createIngrediente(
                uuid = entity.uuid.toString(),
                nombre = entity.nombre,
                cantidad = entity.cantidad.toLong(),
                precio = entity.precio,
                createdAt = timeNow.toString(),
                updatedAt = timeNow.toString(),
                disponible = if(entity.disponible) 1L else 0L
            )
        }
        return db.ingredientesQueries.getLastInsertedIngrediente().executeAsOne().toIngrediente()
    }

    private fun update(entity: Ingrediente): Ingrediente {
        logger.debug { "Se actualiza un ingrediente de la base de datos" }
        val timeNow = LocalDate.now()
        db.ingredientesQueries.updateIngrediente(
            nombre = entity.nombre,
            cantidad = entity.cantidad.toLong(),
            precio = entity.precio,
            updatedAt = timeNow.toString(),
            disponible = if(entity.disponible) 1L else 0L,
            id = entity.id
        )
        return db.ingredientesQueries.getIngredienteById(id = entity.id).executeAsOne().toIngrediente()
    }

    override fun delete(entity: Ingrediente): Boolean {
        logger.info { "Eliminamos un ingrediente" }
        return deleteById(entity.id)
    }

    override fun deleteById(id: Long): Boolean {
        logger.info { "Eliminamos un ingrediente según su id: $id" }
        db.ingredientesQueries.deleteIngrediente(id = id)
        return true
    }

    override fun deleteAll() {
        logger.info { "Eliminamos todos los ingredientes" }
        db.ingredientesQueries.deleteAllIngredientes()
    }
}
package controller

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import error.IngredienteError
import models.Ingrediente
import mu.KotlinLogging
import repositorio.ingredientes.IngredientesRepositoryBase
import service.storage.ingredientes.IngredienteStorageService
import validator.validate
import java.lang.IllegalArgumentException
import java.util.*

class IngredientesController(
    val repository: IngredientesRepositoryBase,
    val storage: IngredienteStorageService
) {

    private val logger = KotlinLogging.logger {  }

    fun findByDisponible(disponible: Boolean): List<Ingrediente> {
        logger.info { "Buscamos segun disponibilidad: $disponible" }
        return repository.findByDisponible(disponible)
    }

    fun findByUuid(uuid: UUID): Result<Ingrediente, IngredienteError> {
        logger.info { "Buscamos segun uuid: $uuid" }
        return repository.findByUuid(uuid)?.let {
            Ok(it)
        }?: run {
            Err(IngredienteError.IngredienteNotFound(uuid.toString()))
        }
    }

    fun findByName(name: String): List<Ingrediente> {
        logger.info { "Buscamos segun name: $name" }
        return repository.findByName(name)
    }

    fun findAll(): List<Ingrediente> {
        logger.info { "Buscamos todos los ingredientes" }
        return repository.findAll()
    }

    fun findById(id: Long): Result<Ingrediente, IngredienteError> {
        logger.info { "Buscamos segun id: $id" }
        return repository.findById(id)?.let {
            Ok(it)
        }?: run {
            Err(IngredienteError.IngredienteNotFound(id.toString()))
        }
    }

    fun save(entity: Ingrediente): Result<Ingrediente, IngredienteError> {
        logger.info { "Guardamos/actualizamos un ingrediente" }
        return entity.validate().andThen {
            Ok(repository.save(it))
        }
    }

    fun delete(entity: Ingrediente): Result<Boolean, IngredienteError> {
        logger.info { "Eliminamos un ingrediente" }
        return if(repository.delete(entity)) {
            Ok(true)
        }else {
            Err(IngredienteError.IngredienteNotFound("${entity.id}"))
        }
    }

    fun deleteById(id: Long): Result<Boolean, IngredienteError> {
        logger.info { "Eliminamos un ingrediente según su id: $id" }
        return if(repository.deleteById(id)){
            Ok(true)
        }else {
            Err(IngredienteError.IngredienteNotFound("$id"))
        }
    }

    fun deleteAll(){
        logger.info { "Eliminamos todos los ingredientes" }
        repository.deleteAll()
    }

    fun exportData(){
        logger.info { "Exportamos los datos a la base de datos desde el fichero" }
        val ingredientes = repository.findAll()
        storage.safeAll(ingredientes)
    }

    fun importData(borramosTodo: Boolean = false){
        logger.info { "Importamos los datos a fichero desde la base de datos" }

        if(borramosTodo) deleteAll()

        val ingredientes = storage.loadAll()

        ingredientes.forEach {
            save(it)
        }
    }
}
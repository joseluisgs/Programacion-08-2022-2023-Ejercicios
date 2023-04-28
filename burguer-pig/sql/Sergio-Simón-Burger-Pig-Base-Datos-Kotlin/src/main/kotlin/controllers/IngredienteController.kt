package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import errors.IngredienteErrors
import models.Ingrediente
import mu.KotlinLogging
import repositories.ingredientes.IngredienteRepository
import service.storage.ingredientes.IngredientesStorage
import validators.validate

private val logger = KotlinLogging.logger {}

class IngredienteController(
    private val ingredienteRepository: IngredienteRepository,
    private val ingredienteStorage: IngredientesStorage,
) {
    fun exportData(items: List<Ingrediente>) {
        ingredienteStorage.saveData(items)
    }

    fun getAll(): List<Ingrediente>{
        logger.info("Obteniendo ingredientes desde la base de datos")

        return ingredienteRepository.findAll().toList()
    }

    fun save(ingrediente: Ingrediente) : Result<Ingrediente, IngredienteErrors> {
        logger.info("Guardando ingrediente en la base de datos" )

        return ingrediente.validate().andThen {
            Ok(ingredienteRepository.save(it))
        }
    }

    fun saveAll(ingredientes: List<Ingrediente>)  {
        logger.info("Guardando todos los ingredientes en la base de datos")

        ingredienteRepository.saveAll(ingredientes)
    }

    fun delete(ingrediente: Ingrediente) : Result<Boolean, IngredienteErrors> {
        logger.info("Eliminar ingrediente $ingrediente")

        return ingrediente.validate().andThen {
            Ok(ingredienteRepository.delete(ingrediente))
        }
    }

    fun deleteAll(ingredientes: List<Ingrediente>)  {
        logger.info("Eliminar todos los ingredientes en la base de datos")

        ingredienteRepository.deleteAll()
    }

    fun deleteById(id: Long) : Boolean {
        logger.info("Eliminar ingrediente por id: $id")

        return ingredienteRepository.deleteById(id)
    }

    fun importData(){
        logger.info { "Importando información desde un fichero" }

        ingredienteStorage.loadData().forEach {
            ingredienteRepository.save(it)
        }
    }

    fun contar(): Long {
        logger.info("Contando el numero de ingredientes")

        return ingredienteRepository.count()
    }

    fun findById(id: Long): Result<Ingrediente, IngredienteErrors> {
        logger.info("Obteniendo ingrediente por id: $id")

        ingredienteRepository.findById(id).let {
            return if( it != null) {
                Ok(it)
            } else {
                Err(IngredienteErrors.IngredienteNoEncontrado("Ingrediente no encontrado al buscarlo por id: $id"))
            }
        }
    }

    fun existsById(id: Long): Boolean {
        logger.info("Comprobando si existe el ingrediente con id: $id")

        return ingredienteRepository.existsById(id)
    }
}
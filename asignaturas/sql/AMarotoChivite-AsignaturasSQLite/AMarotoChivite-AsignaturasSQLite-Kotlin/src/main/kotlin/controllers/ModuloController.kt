package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.ModuloError
import models.Modulo
import repositories.ModuloRepository
import services.storages.ModuloStorageCsv
import validators.ModuloValidator

private val logger = mu.KotlinLogging.logger { }

class ModuloController(
    private val repository: ModuloRepository,
    private val storage: ModuloStorageCsv
) {

    fun getAll(): List<Modulo> {
        logger.info { "Controller: Devolviendo todos los items" }

        return repository.getAll()
    }

    fun getById(item: Modulo): Result<Modulo, ModuloError> {
        logger.info { "Controller: Obtener un item por ID" }

        return when (val it = repository.getById(item)) {
            null -> Err(ModuloError.NotFound("No se ha encontrado el item con id ${item.uuid}"))
            else -> Ok(it)
        }
    }

    fun saveItem(item: Modulo): Result<Modulo, ModuloError> {
        logger.info { "Controller: Guardando ${item::class.simpleName}" }

        return when (val validationResult = ModuloValidator.validate(item)) {
            is Ok -> Ok(repository.saveItem(item))
            is Err -> validationResult
        }
    }

    fun deleteItem(item: Modulo): Result<Boolean, ModuloError> {
        logger.info { "Controller: Borrando ${item::class.simpleName} con id ${item.uuid}" }

        return when (repository.deleteItem(item)) {
            true -> Ok(true)
            else -> Err(ModuloError.NotFound("No se ha encontrado el item con id ${item.uuid}"))
        }
    }

    fun deleteAllItem(): Result<Boolean, ModuloError> {
        logger.info { "Controller: Borrando todos los items" }

        return when (repository.deleteAll()) {
            true -> Ok(true)
            else -> Err(ModuloError.NotFound("No se han podido borrar todos los items"))
        }
    }

    fun exportFile(): Boolean {
        val data = repository.getAll()
        return storage.writeFile(data)
    }

    fun importFile(): List<Modulo> {
        return storage.readFile()
    }

}
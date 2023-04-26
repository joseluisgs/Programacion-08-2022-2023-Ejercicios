package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.ProfesorError
import models.Profesor
import repositories.ProfesorRepository
import services.storages.ProfesorStorageCsv
import validators.ProfesorValidator

private val logger = mu.KotlinLogging.logger { }

class ProfesorController(
    private val repository: ProfesorRepository,
    private val storage: ProfesorStorageCsv
) {

    fun getAll(): List<Profesor> {
        logger.info { "Controller: Devolviendo todos los items" }

        return repository.getAll()
    }

    fun getById(item: Profesor): Result<Profesor, ProfesorError> {
        logger.info { "Controller: Obtener un item por ID" }

        return when (val it = repository.getById(item)) {
            null -> Err(ProfesorError.NotFound("No se ha encontrado el item con id ${item.id}"))
            else -> Ok(it)
        }
    }

    fun saveItem(item: Profesor): Result<Profesor, ProfesorError> {
        logger.info { "Controller: Guardando ${item::class.simpleName}" }

        return when (val validationResult = ProfesorValidator.validate(item)) {
            is Ok -> Ok(repository.saveItem(item))
            is Err -> validationResult
        }
    }

    fun deleteItem(item: Profesor): Result<Boolean, ProfesorError> {
        logger.info { "Controller: Borrando ${item::class.simpleName} con id ${item.id}" }

        return when (repository.deleteItem(item)) {
            true -> Ok(true)
            else -> Err(ProfesorError.NotFound("No se ha encontrado el item con id ${item.id}"))
        }
    }

    fun deleteAllItem(): Result<Boolean, ProfesorError> {
        logger.info { "Controller: Borrando todos los items" }

        return when (repository.deleteAll()) {
            true -> Ok(true)
            else -> Err(ProfesorError.NotFound("No se han podido borrar todos los items"))
        }
    }

    fun exportFile(): Boolean {
        val data = repository.getAll()
        return storage.writeFile(data)
    }

    fun importFile(): List<Profesor> {
        return storage.readFile()
    }

}
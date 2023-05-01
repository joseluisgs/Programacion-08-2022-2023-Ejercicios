package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.DocenciaError
import models.Docencia
import repositories.DocenciaRepository
import services.storages.DocenciaStorageJson
import validators.DocenciaValidator

private val logger = mu.KotlinLogging.logger { }

class DocenciaController(
    private val repository: DocenciaRepository,
    private val storage: DocenciaStorageJson
) {

    fun getAll(): List<Docencia> {
        logger.info { "Controller: Devolviendo todos los items" }

        return repository.getAll()
    }

    fun getById(item: Docencia): Result<Docencia, DocenciaError> {
        logger.info { "Controller: Obtener un item por ID" }

        return when (val it = repository.getById(item)) {
            null -> Err(DocenciaError.NotFound("No se ha encontrado el item con id_profesor ${item.idProfesor} y uuid_modulo ${item.uuidModulo}"))
            else -> Ok(it)
        }
    }

    fun saveItem(item: Docencia): Result<Docencia, DocenciaError> {
        logger.info { "Controller: Guardando ${item::class.simpleName}" }

        return when (val validationResult = DocenciaValidator.validate(item)) {
            is Ok -> Ok(repository.saveItem(item))
            is Err -> validationResult
        }
    }

    fun deleteItem(item: Docencia): Result<Boolean, DocenciaError> {
        logger.info { "Controller: Borrando ${item::class.simpleName} con id_profesor ${item.idProfesor} y uuid_modulo ${item.uuidModulo}" }

        return when (repository.deleteItem(item)) {
            true -> Ok(true)
            else -> Err(DocenciaError.NotFound("No se ha encontrado el item con id_profesor ${item.idProfesor} y uuid_modulo ${item.uuidModulo}"))
        }
    }

    fun deleteAllItem(): Result<Boolean, DocenciaError> {
        logger.info { "Controller: Borrando todos los items" }

        return when (repository.deleteAll()) {
            true -> Ok(true)
            else -> Err(DocenciaError.NotFound("No se han podido borrar todos los items"))
        }
    }

    fun exportFile(): Boolean {
        val data = repository.getAll()
        return storage.writeFile(data)
    }

    fun importFile(): List<Docencia> {
        return storage.readFile()
    }

}
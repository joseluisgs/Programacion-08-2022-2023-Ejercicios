package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.BurguerError
import models.Burguer
import repositories.BurguerRepository
import services.storages.BurguerStorageCsv
import services.storages.BurguerStorageJson
import validators.BurguerValidator

private val logger = mu.KotlinLogging.logger { }

class BurguerController (
    private val repository: BurguerRepository,
    private val storageCsv: BurguerStorageCsv,
    private val storageJson: BurguerStorageJson,
) {

    fun getAll(): List<Burguer> {
        logger.info { "${this::class.simpleName}: Devolviendo todos los items" }

        return repository.getAllBurguer()
    }

    fun getById(item: Burguer): Result<Burguer, BurguerError> {
        logger.info { "${this::class.simpleName}: Obtener un item por ID" }

        return when (val it = repository.getById(item)) {
            null -> Err(BurguerError.NotFound("No se ha encontrado el item con id ${item.uuid}"))
            else -> Ok(it)
        }
    }

    fun saveItem(item: Burguer): Result<Burguer, BurguerError> {
        logger.info { "${this::class.simpleName}: Guardando ${item::class.simpleName}" }

        return when (val validationResult = BurguerValidator.validate(item)) {
            is Ok -> Ok(repository.saveItem(item))
            is Err -> validationResult
        }
    }

    fun deleteItem(item: Burguer): Result<Boolean, BurguerError> {
        logger.info { "${this::class.simpleName}: Borrando ${item::class.simpleName} con id ${item.uuid}" }

        return when (repository.deleteItem(item)) {
            true -> Ok(true)
            else -> Err(BurguerError.NotFound("No se ha encontrado el item con id ${item.uuid}"))
        }
    }

    fun deleteAllItem(): Result<Boolean, BurguerError> {
        logger.info { "${this::class.simpleName}: Borrando todos los items" }

        return when (repository.deleteAll()) {
            true -> Ok(true)
            else -> Err(BurguerError.NotFound("No se han podido borrar todos los items"))
        }
    }

    fun exportFileToCsv(): Boolean{
        val data = repository.getAllBurguer()
        storageCsv.writeFile(data)
        return true
    }

    fun importFileCsv(): List<Burguer>{
        return storageCsv.readFile()
    }

    fun exportFileToJson(): Boolean{
        val data = repository.getAllBurguer()
        storageJson.writeFile(data)
        return true
    }

    fun importFileJson(): List<Burguer>{
        return storageJson.readFile()
    }

}
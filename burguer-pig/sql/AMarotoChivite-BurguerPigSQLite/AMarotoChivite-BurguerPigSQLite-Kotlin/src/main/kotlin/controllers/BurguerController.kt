package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.BurguerError
import models.Burguer
import repositories.BurguerRepository
import services.storages.BurguerStorageCsv
import services.storages.BurguerStorageJson
import services.storages.base.IStorageGeneral
import validators.BurguerValidator

private val logger = mu.KotlinLogging.logger { }

class BurguerController ( private val repository: BurguerRepository, private val storageCsv: BurguerStorageCsv, private val storageJson: BurguerStorageJson) {

    private var currentFileType: FileType = FileType.JSON // Valor predeterminado

    fun setFileType(type: FileType){
        this.currentFileType = type
    }

    fun getAll(): List<Burguer> {
        logger.info { "Controller: Devolviendo todos los items" }

       return repository.getAll()
    }

    fun getById(item: Burguer): Result<Burguer, BurguerError> {
        logger.info { "Controller: Obtener un item por ID" }

        return when (val it = repository.getById(item)) {
            null -> Err(BurguerError.NotFound("No se ha encontrado el item con id ${item.uuid}"))
            else -> Ok(it)
        }
    }

    fun saveItem(item: Burguer): Result<Burguer, BurguerError> {
        logger.info { "Controller: Guardando ${item::class.simpleName}" }

        return when (val validationResult = BurguerValidator.validate(item)) {
            is Ok -> Ok(repository.saveItem(item))
            is Err -> validationResult
        }
    }

    fun deleteItem(item: Burguer): Result<Boolean, BurguerError> {
        logger.info { "Controller: Borrando ${item::class.simpleName} con id ${item.uuid}" }

        return when (repository.deleteItem(item)) {
            true -> Ok(true)
            else -> Err(BurguerError.NotFound("No se ha encontrado el item con id ${item.uuid}"))
        }
    }

    fun deleteAllItem(): Result<Boolean, BurguerError> {
        logger.info { "Controller: Borrando todos los items" }

        return when (repository.deleteAll()) {
            true -> Ok(true)
            else -> Err(BurguerError.NotFound("No se han podido borrar todos los items"))
        }
    }

    fun exportFile(): Boolean{
        val data = repository.getAll()
        return when(currentFileType) {
            FileType.CSV -> storageCsv.writeFile(data)
            FileType.JSON -> storageJson.writeFile(data)
        }
    }

    fun importFile(): List<Burguer>{
        return when(currentFileType) {
            FileType.CSV -> storageCsv.readFile()
            FileType.JSON -> storageJson.readFile()
        }
    }

    enum class FileType {
        CSV, JSON
    }

}
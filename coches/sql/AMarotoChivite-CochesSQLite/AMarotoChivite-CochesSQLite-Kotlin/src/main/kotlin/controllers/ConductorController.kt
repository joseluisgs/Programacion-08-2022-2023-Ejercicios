package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.ConductorError
import models.Conductor
import repositories.base.IRepositoryCrud
import services.storages.base.IStorageGeneral
import validators.ConductorValidator

private val logger = mu.KotlinLogging.logger { }

class ConductorController(
    private val conductorRepository: IRepositoryCrud<Conductor>,
    private val conductorStorage: IStorageGeneral<Conductor>
) {
    fun getAll(): List<Conductor> {
        logger.info { "Controller: Devolviendo todos los items" }

        return conductorRepository.getAll()
    }

    fun getById(item: Conductor): Result<Conductor, ConductorError> {
        logger.info { "Controller: Obtener un item por ID" }

        return when (val it = conductorRepository.getById(item)) {
            null -> Err(ConductorError.NotFound("No se ha encontrado el item con id ${item.uuid}"))
            else -> Ok(it)
        }
    }

    fun saveItem(item: Conductor): Result<Conductor, ConductorError> {
        logger.info { "Controller: Guardando ${item::class.simpleName}" }

        return when (val validationResult = ConductorValidator.validate(item)) {
            is Ok -> Ok(conductorRepository.saveItem(item))
            is Err -> validationResult
        }
    }

    fun deleteItem(item: Conductor): Result<Boolean, ConductorError> {
        logger.info { "Controller: Borrando ${item::class.simpleName} con id ${item.uuid}" }

        return when (conductorRepository.deleteItem(item)) {
            true -> Ok(true)
            else -> Err(ConductorError.NotFound("No se ha encontrado el item con id ${item.uuid}"))
        }
    }

    // Exportamos datos del REPOSITORY conectado a la BBDD, al formato de fichero deseado
    fun exportDataToFile(typeFile: String): Boolean {
        when (typeFile) {
            "json" -> conductorStorage.writeFileToJson(conductorRepository.getAll().toList())
            "csv" -> conductorStorage.writeFileToCsv(conductorRepository.getAll().toList())
            "xml" -> conductorStorage.writeFileToXml(conductorRepository.getAll().toList())
            else -> throw IllegalArgumentException("Tipo de fichero no apto: $typeFile")
        }
        return true
    }

    // Importamos datos mediante el STORAGE, en función del fichero que nos aporten y lo introducimos a la BBDD conectado al REPOSITORY
    fun importDataToDataBase(typeFile: String): Boolean {
        when (typeFile) {
            "json" -> {
                conductorStorage.readFileOfJson().forEach {
                    conductorRepository.saveItem(it)
                }
            }

            "csv" -> {
                conductorStorage.readFileOfCsv().forEach {
                    conductorRepository.saveItem(it)
                }
            }

            "xml" -> {
                conductorStorage.readFileOfXml()!!.forEach {
                    conductorRepository.saveItem(it)
                }
            }

            else -> throw IllegalArgumentException("Tipo de fichero no apto: $typeFile")
        }

        return true
    }

    // Importamos datos mediante el STORAGE, en función del fichero que nos aporten, por si queremos manipular dentro de la lógica del programa
    fun importDataToMain(typeFile: String): List<Conductor>? {
        return when (typeFile) {
            "json" -> {
                conductorStorage.readFileOfJson()
            }

            "csv" -> {
                conductorStorage.readFileOfCsv()
            }

            "xml" -> {
                conductorStorage.readFileOfXml()
            }

            else -> throw IllegalArgumentException("Unsupported file type: $typeFile")
        }
    }
}
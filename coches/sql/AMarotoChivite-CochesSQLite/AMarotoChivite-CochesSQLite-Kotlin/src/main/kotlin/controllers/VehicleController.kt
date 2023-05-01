package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.VehicleError
import models.Vehicle
import repositories.base.IRepositoryCrud
import services.storages.base.IStorageGeneral
import validators.VehicleValidator

private val logger = mu.KotlinLogging.logger { }

class VehicleController(
    private val vehicleRepository: IRepositoryCrud<Vehicle>,
    private val vehicleStorage: IStorageGeneral<Vehicle>
) {
    fun getAll(): List<Vehicle> {
        logger.info { "Controller: Devolviendo todos los items" }

        return vehicleRepository.getAll()
    }

    fun getById(item: Vehicle): Result<Vehicle, VehicleError> {
        logger.info { "Controller: Obtener un item por ID" }

        return when (val it = vehicleRepository.getById(item)) {
            null -> Err(VehicleError.NotFound("No se ha encontrado el item con id ${item.uuid}"))
            else -> Ok(it)
        }
    }

    fun saveItem(item: Vehicle): Result<Vehicle, VehicleError> {
        logger.info { "Controller: Guardando ${item::class.simpleName}" }

        return when (val validationResult = VehicleValidator.validate(item)) {
            is Ok -> Ok(vehicleRepository.saveItem(item))
            is Err -> validationResult
        }
    }

    fun deleteItem(item: Vehicle): Result<Boolean, VehicleError> {
        logger.info { "Controller: Borrando ${item::class.simpleName} con id ${item.uuid}" }

        return when (vehicleRepository.deleteItem(item)) {
            true -> Ok(true)
            else -> Err(VehicleError.NotFound("No se ha encontrado el item con id ${item.uuid}"))
        }
    }

    // Exportamos datos del REPOSITORY conectado a la BBDD, al formato de fichero deseado
    fun exportDataToFile(typeFile: String): Boolean {
        when (typeFile) {
            "json" -> vehicleStorage.writeFileToJson(vehicleRepository.getAll().toList())
            "csv" -> vehicleStorage.writeFileToCsv(vehicleRepository.getAll().toList())
            "xml" -> vehicleStorage.writeFileToXml(vehicleRepository.getAll().toList())
            else -> throw IllegalArgumentException("Tipo de fichero no apto: $typeFile")
        }
        return true
    }

    // Importamos datos mediante el STORAGE, en función del fichero que nos aporten y lo introducimos a la BBDD conectado al REPOSITORY
    fun importDataToDataBase(typeFile: String): Boolean {
        when (typeFile) {
            "json" -> {
                vehicleStorage.readFileOfJson().forEach {
                    vehicleRepository.saveItem(it)
                }
            }

            "csv" -> {
                vehicleStorage.readFileOfCsv().forEach {
                    vehicleRepository.saveItem(it)
                }
            }

            "xml" -> {
                vehicleStorage.readFileOfXml()!!.forEach {
                    vehicleRepository.saveItem(it)
                }
            }

            else -> throw IllegalArgumentException("Unsupported file type: $typeFile")
        }
        return true
    }

    // Importamos datos mediante el STORAGE, en función del fichero que nos aporten, por si queremos manipular dentro de la lógica del programa
    fun importDataToMain(typeFile: String): List<Vehicle>? {
        return when (typeFile) {
            "json" -> {
                vehicleStorage.readFileOfJson()
            }

            "csv" -> {
                vehicleStorage.readFileOfCsv()
            }

            "xml" -> {
                vehicleStorage.readFileOfXml()
            }

            else -> throw IllegalArgumentException("Unsupported file type: $typeFile")
        }
    }
}
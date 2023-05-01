package services.storage.conductor

import com.github.michaelbull.result.get
import config.AppConfig
import controllers.coche.CocheController
import dto.CocheDto
import dto.CochesDto
import dto.ConductorDto
import exceptions.CocheFileException
import exceptions.ConductorFileException
import mappers.toClass
import mappers.toDto
import models.Coche
import models.Conductor
import mu.KotlinLogging
import repositories.coche.CocheRepositoryMap
import services.storage.coche.CocheFileCsv
import java.io.File
import java.io.IOException
import java.util.UUID

private val logger = KotlinLogging.logger {  }

object ConductorFileCsv: ConductorStorageService {
    private val localPath = "${AppConfig.APP_DATA}${File.separator}conductor.csv"
    private val localPathResources = "${AppConfig.APP_PATH_RESOURCES}conductor.csv"

    override fun saveAll(elements: List<Conductor>): List<Conductor> {
        logger.debug { "ConductorFileCsv ->\tsaveAll" }
        val file = File(localPath)
        if (file.exists() && !file.canWrite()) throw ConductorFileException.ConductorFileCantWrite("CSV")
        file.writeText("uuid, nombre, fechaCarnet\n")
        elements.map { it.toDto() }.forEach {
            file.appendText("${it.uuid},${it.nombre},${it.fechaCarnet}\n")
        }
        return elements
    }

    /**
     * La lectura la hace sobre el fichero en resources,
     * los coches están en otro fichero csv en resources.
     */
    override fun loadAll(): List<Conductor> {
        logger.debug { "ConductorFileCsv ->\tloadAll" }
        val file = File(localPathResources)
        if (!file.exists() || !file.canRead()) throw ConductorFileException.ConductorFileCantReed("CSV")

        return file.readLines()
            .drop(1)
            .map { it.split(",") }
            .map { campo -> ConductorDto(
                campo[0],
                campo[1],
                campo[2],
                buscarCoches(UUID.fromString(campo[0]))
            ).toClass() }
    }

    private fun buscarCoches(uuid: UUID?): CochesDto {
        val cocheController = CocheController(
            CocheRepositoryMap(),
            CocheFileCsv
        )
        cocheController.importData()
        return CochesDto(
            cocheController.findAll()
                .filter { it.idConductor == uuid }
                .map { it.toDto() }
        )
    }
}
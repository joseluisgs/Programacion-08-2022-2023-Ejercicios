package services.storage.coche

import config.AppConfig
import dto.CocheDto
import exceptions.CocheFileException
import mappers.toClass
import mappers.toDto
import models.Coche
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {  }

object CocheFileCsv: CocheStorageService {
    private val localPath = "${AppConfig.APP_DATA}${File.separator}coche.csv"
    private val localPathResources = "${AppConfig.APP_PATH_RESOURCES}coche.csv"

    override fun saveAll(elements: List<Coche>): List<Coche> {
        logger.debug { "CocheFileCsv ->\tsaveAll" }
        val file = File(localPath)
        if (file.exists() && !file.canWrite()) throw CocheFileException.CocheFileCantWrite("CSV")
        file.writeText("id,marca,modelo,precio,motor,idConductor\n")
        elements.map { it.toDto() }.forEach {
            file.appendText("${it.id},${it.marca},${it.modelo},${it.precio},${it.motor},${it.idConductor}\n")
        }
        return elements
    }

    /**
     * La lectura la hace sobre el fichero en resources.
     */
    override fun loadAll(): List<Coche> {
        logger.debug { "CocheFileCsv ->\tloadAll" }
        val file = File(localPathResources)
        if (!file.exists() || !file.canRead()) throw CocheFileException.CocheFileCantReed("CSV")
        return file.readLines()
            .drop(1)
            .map { it.split(",") }
            .map { campo -> CocheDto(
                campo[0],
                campo[1],
                campo[2],
                campo[3],
                campo[4],
                campo[5]
            ).toClass() }
    }
}
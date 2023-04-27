package services.storage.modulo

import config.AppConfig
import dto.ModuloDto
import exceptions.ModuloFileException
import mappers.toClass
import mappers.toDto
import models.Modulo
import mu.KotlinLogging
import java.io.File
import java.io.IOException

private val logger = KotlinLogging.logger {  }

object ModuloFileCsv: ModuloStorageService{
    private val localPath = "${AppConfig.APP_DATA}${File.separator}modulo.csv"
    private val resourcesPath = "${AppConfig.APP_PATH_RESOURCES}modulo.csv"

    override fun saveAll(elements: List<Modulo>): List<Modulo> {
        logger.debug { "ModuloFileCsv ->\tsaveAll" }
        val file = File(localPath)
        if (file.exists() && !file.canWrite()) throw ModuloFileException.ModuloFileCantWrite("CSV")
        file.writeText("uuid,nombre,curso,grado\n")
        elements.map { it.toDto() }.forEach {
            file.appendText(
                "${it.uuid},${it.nombre},${it.curso},${it.grado}\n"
            )
        }
        return elements
    }

    override fun loadAll(): List<Modulo> {
        logger.debug { "ModuloFileCsv ->\tloadAll" }
        val file = File(resourcesPath)
        if (!file.exists() || !file.canRead()) throw ModuloFileException.ModuloFileCantReed("CSV")
        return file.readLines()
            .drop(1)
            .map { it.split(",") }
            .map { ModuloDto(
                it[0],
                it[1],
                it[2],
                it[3],
            )}.map { it.toClass() }
    }

}
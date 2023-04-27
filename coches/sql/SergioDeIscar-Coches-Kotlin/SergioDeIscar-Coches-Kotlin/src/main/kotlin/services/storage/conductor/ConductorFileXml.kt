package services.storage.conductor

import config.AppConfig
import dto.CochesDto
import dto.ConductoresDto
import exceptions.CocheFileException
import exceptions.ConductorFileException
import mappers.toClass
import mappers.toDto
import models.Coche
import models.Conductor
import mu.KotlinLogging
import org.simpleframework.xml.core.Persister
import java.io.File

private val logger = KotlinLogging.logger {  }

object ConductorFileXml: ConductorStorageService {
    private val localPath = "${AppConfig.APP_DATA}${File.separator}conductor.xml"
    override fun saveAll(elements: List<Conductor>): List<Conductor> {
        logger.debug { "CocheFileXml ->\tsaveAll" }
        val file = File(localPath)
        if (file.exists() && !file.canWrite()) throw ConductorFileException.ConductorFileCantWrite("XML")
        val persister = Persister()
        persister.write(ConductoresDto(elements.map { it.toDto() }), file)
        return elements
    }

    override fun loadAll(): List<Conductor> {
        logger.debug { "CocheFileXml ->\tloadAll" }
        val file = File(localPath)
        if (!file.exists() || !file.canRead()) throw ConductorFileException.ConductorFileCantWrite("XML")
        val persister = Persister()
        return persister.read(ConductoresDto::class.java, file).conductores.map { it.toClass() }
    }
}
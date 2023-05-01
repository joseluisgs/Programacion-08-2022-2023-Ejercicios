package services.storage.coche

import config.AppConfig
import dto.CochesDto
import exceptions.CocheFileException
import mappers.toClass
import mappers.toDto
import models.Coche
import mu.KotlinLogging
import org.simpleframework.xml.core.Persister
import java.io.File

private val logger = KotlinLogging.logger {  }

object CocheFileXml: CocheStorageService {
    private val localPath = "${AppConfig.APP_DATA}${File.separator}coche.xml"
    override fun saveAll(elements: List<Coche>): List<Coche> {
        logger.debug { "CocheFileXml ->\tsaveAll" }
        val file = File(localPath)
        if (file.exists() && !file.canWrite()) throw CocheFileException.CocheFileCantWrite("XML")
        val persister = Persister()
        persister.write(CochesDto(elements.map { it.toDto() }), file)
        return elements
    }

    override fun loadAll(): List<Coche> {
        logger.debug { "CocheFileXml ->\tloadAll" }
        val file = File(localPath)
        if (!file.exists() || !file.canRead()) throw CocheFileException.CocheFileCantReed("XML")
        val persister = Persister()
        return persister.read(CochesDto::class.java, file).coches.map { it.toClass() }
    }
}
package services.storage.modulo

import config.AppConfig
import dto.ModulosDto
import exceptions.ModuloFileException
import mappers.toClass
import mappers.toDto
import models.Modulo
import mu.KotlinLogging
import org.simpleframework.xml.core.Persister
import java.io.File
import java.io.IOException

private val logger = KotlinLogging.logger {  }

object ModuloFileXml: ModuloStorageService {
    private val localPath = "${AppConfig.APP_DATA}${File.separator}modulo.xml"

    override fun saveAll(elements: List<Modulo>): List<Modulo> {
        logger.debug { "ModuloFileXml ->\tsaveAll" }
        val file = File(localPath)
        if (file.exists() && !file.canWrite()) throw ModuloFileException.ModuloFileCantWrite("XML")
        val persister = Persister()
        persister.write(ModulosDto(elements.map { it.toDto() }), file)
        return elements
    }

    override fun loadAll(): List<Modulo> {
        val file = File(localPath)
        if (!file.exists() || !file.canRead()) throw ModuloFileException.ModuloFileCantReed("XML")
        val persister = Persister()
        return persister.read(ModulosDto::class.java, file).modulos.map { it.toClass() }
    }
}
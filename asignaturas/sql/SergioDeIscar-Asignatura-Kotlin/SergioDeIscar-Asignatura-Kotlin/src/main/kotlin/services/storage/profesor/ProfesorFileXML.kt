package services.storage.profesor

import config.AppConfig
import dto.ProfesoresDto
import mappers.toClass
import mappers.toDto
import models.Profesor
import mu.KotlinLogging
import org.simpleframework.xml.core.Persister
import java.io.File
import java.io.IOException

private val logger = KotlinLogging.logger {  }

object ProfesorFileXML: ProfesorStorageService {
    private val localPath = "${AppConfig.APP_DATA}${File.separator}profesor.xml"

    override fun saveAll(elements: List<Profesor>): List<Profesor> {
        logger.debug { "ProfesorFileXML ->\tsaveAll" }
        val file = File(localPath)
        if (file.exists() && !file.canWrite()) throw IOException("ERROR: ProfesorFileXML ->\tNo se puede escribir en el fichero XML")
        val persister = Persister()
        persister.write(ProfesoresDto(elements.map { it.toDto() }), file)
        return elements
    }

    override fun loadAll(): List<Profesor> {
        logger.debug { "ProfesorFileXML ->\tloadAll" }
        val file = File(localPath)
        if (!file.exists() || !file.canRead()) throw IOException("ERROR: ProfesorFileXML ->\tNo se puede leer en el fichero XML")
        val persister = Persister()
        return persister.read(ProfesoresDto::class.java, file).profesores.map { it.toClass() }
    }
}
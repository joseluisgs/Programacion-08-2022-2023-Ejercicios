package service.storage.profesores

import config.ConfigApp
import dto.ConjuntoProfesoresDto
import mapper.toDtos
import mapper.toProfesores
import models.Profesor
import mu.KotlinLogging
import org.simpleframework.xml.core.Persister
import java.io.File

class ProfesoresStorageServiceXml: ProfesorStorageService {

    private val logger = KotlinLogging.logger {  }

    private val config = ConfigApp
    private val file = File(config.APP_DATA+File.separator+"profesores.xml")

    private val persister = Persister()

    override fun safeAll(entites: List<Profesor>) {
        logger.debug { "Guardamos todos los profesores en el fichero XML." }
        persister.write(entites.toDtos(), file)
    }

    override fun loadAll(): List<Profesor> {
        logger.debug { "Cargamos todos los profesores del fichero XML." }
        if(!file.exists()) return emptyList()
        return (persister.read(ConjuntoProfesoresDto::class.java, file)).toProfesores()
    }
}
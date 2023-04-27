package service.storage.profesores

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import config.ConfigApp
import dto.ConjuntoProfesoresDto
import mapper.toDtos
import mapper.toProfesores
import models.Profesor
import mu.KotlinLogging
import java.io.File

@ExperimentalStdlibApi
class ProfesoresStorageServiceJson: ProfesorStorageService {

    private val logger = KotlinLogging.logger {  }

    private val config = ConfigApp
    private val file = File(config.APP_DATA+ File.separator+"profesores.json")

    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val adapter = moshi.adapter<ConjuntoProfesoresDto>()

    override fun safeAll(entites: List<Profesor>) {
        logger.debug { "Guardamos todos los profesores en el fichero JSON." }
        file.writeText(adapter.indent("   ").toJson(entites.toDtos()))
    }

    override fun loadAll(): List<Profesor> {
        logger.debug { "Cargamos todos los profesores del fichero JSON." }
        if(!file.exists()) return emptyList()
        return (adapter.fromJson(file.readText()))!!.toProfesores()
    }
}
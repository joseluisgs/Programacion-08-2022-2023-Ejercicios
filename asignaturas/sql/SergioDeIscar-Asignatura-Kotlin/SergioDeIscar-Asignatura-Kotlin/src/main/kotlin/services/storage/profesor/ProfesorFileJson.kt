package services.storage.profesor

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import config.AppConfig
import dto.ProfesoresDto
import mappers.toClass
import mappers.toDto
import models.Profesor
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {  }

object ProfesorFileJson: ProfesorStorageService {
    private val localPath = "${AppConfig.APP_DATA}${File.separator}profesor.json"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @OptIn(ExperimentalStdlibApi::class)
    override fun saveAll(elements: List<Profesor>): List<Profesor> {
        logger.debug { "ProfesorFileJson ->\tsaveAll" }
        val file = File(localPath)
        if (file.exists() && !file.canWrite()) throw Exception("ERROR: ProfesorFileJson ->\tNo se puede escribir en el fichero JSON")
        val jsonAdapter = moshi.adapter<ProfesoresDto>()
        file.writeText(jsonAdapter.indent("\t").toJson(ProfesoresDto(elements.map { it.toDto() })))
        return elements
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun loadAll(): List<Profesor> {
        logger.debug { "ProfesorFileJson ->\tloadAll" }
        val file = File(localPath)
        if (!file.exists() || !file.canRead()) throw Exception("ERROR: ProfesorFileJson ->\tNo se puede leer en el fichero JSON")
        val jsonAdapter = moshi.adapter<ProfesoresDto>()
        return jsonAdapter.indent("\t").fromJson(file.readText())?.let { it.profesores.map { it.toClass() } } ?: return emptyList()
    }
}
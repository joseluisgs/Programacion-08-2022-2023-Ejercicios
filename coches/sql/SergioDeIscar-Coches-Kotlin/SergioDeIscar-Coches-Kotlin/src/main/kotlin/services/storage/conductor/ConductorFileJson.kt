package services.storage.conductor

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
import java.io.File

private val logger = KotlinLogging.logger {  }

object ConductorFileJson: ConductorStorageService {
    private val localPath = "${AppConfig.APP_DATA}${File.separator}conductor.json"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    @OptIn(ExperimentalStdlibApi::class)
    override fun saveAll(elements: List<Conductor>): List<Conductor> {
        logger.debug { "CocheFileJson ->\tsaveAll" }
        val file = File(localPath)
        if (file.exists() && !file.canWrite()) throw ConductorFileException.ConductorFileCantWrite("JSON")
        val jsonAdapter = moshi.adapter<ConductoresDto>()
        file.writeText(jsonAdapter.indent("\t").toJson(ConductoresDto(elements.map { it.toDto() })))
        return elements
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun loadAll(): List<Conductor> {
        logger.debug { "CocheFileJson ->\tloadAll" }
        val file = File(localPath)
        if (!file.exists() || !file.canRead()) throw ConductorFileException.ConductorFileCantReed("JSON")
        val jsonAdapter = moshi.adapter<ConductoresDto>()
        return jsonAdapter.fromJson(file.readText())?.let { it.conductores.map { it.toClass() } } ?: emptyList()
    }
}
package services.storage.coche

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import config.AppConfig
import dto.CochesDto
import exceptions.CocheFileException
import mappers.toClass
import mappers.toDto
import models.Coche
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {  }

object CocheFileJson: CocheStorageService {
    private val localPath = "${AppConfig.APP_DATA}${File.separator}coche.json"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    @OptIn(ExperimentalStdlibApi::class)
    override fun saveAll(elements: List<Coche>): List<Coche> {
        logger.debug { "CocheFileJson ->\tsaveAll" }
        val file = File(localPath)
        if (file.exists() && !file.canWrite()) throw CocheFileException.CocheFileCantWrite("JSON")
        val jsonAdapter = moshi.adapter<CochesDto>()
        file.writeText(jsonAdapter.indent("\t").toJson(CochesDto(elements.map { it.toDto() })))
        return elements
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun loadAll(): List<Coche> {
        logger.debug { "CocheFileJson ->\tloadAll" }
        val file = File(localPath)
        if (!file.exists() || !file.canRead()) throw CocheFileException.CocheFileCantReed("JSON")
        val jsonAdapter = moshi.adapter<CochesDto>()
        return jsonAdapter.fromJson(file.readText())?.let { it.coches.map { it.toClass() } } ?: emptyList()
    }
}
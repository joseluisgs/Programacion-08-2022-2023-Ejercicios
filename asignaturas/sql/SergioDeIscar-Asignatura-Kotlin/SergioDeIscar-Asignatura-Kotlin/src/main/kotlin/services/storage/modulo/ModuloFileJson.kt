package services.storage.modulo

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import config.AppConfig
import dto.ModulosDto
import exceptions.ModuloFileException
import mappers.toClass
import mappers.toDto
import models.Modulo
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {  }

object ModuloFileJson: ModuloStorageService {
    private val localPath = "${AppConfig.APP_DATA}${File.separator}modulo.json"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @OptIn(ExperimentalStdlibApi::class)
    override fun saveAll(elements: List<Modulo>): List<Modulo> {
        logger.debug { "ModuloFileJson ->\tsaveAll" }
        val file = File(localPath)
        if (file.exists() && !file.canWrite()) throw ModuloFileException.ModuloFileCantWrite("JSON")
        val jsonAdapter = moshi.adapter<ModulosDto>()
        file.writeText(jsonAdapter.indent("\t").toJson(ModulosDto(elements.map { it.toDto() })))
        return elements
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun loadAll(): List<Modulo> {
        logger.debug { "ModuloFileJson ->\tloadAll" }
        val file = File(localPath)
        if (!file.exists() || !file.canRead()) throw ModuloFileException.ModuloFileCantWrite("JSON")
        val jsonAdapter = moshi.adapter<ModulosDto>()
        return jsonAdapter.indent("\t").fromJson(file.readText())?.let { it.modulos.map { it.toClass() } } ?: return emptyList()
    }
}
package services.conductor

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import config.AppConfig
import dto.ConductorListDto
import mapper.toConductorList
import mapper.toDtoList
import models.Conductor
import mu.KotlinLogging
import utils.localDateFromString
import java.io.File
import java.nio.file.Files.createFile
import java.util.*

@OptIn(ExperimentalStdlibApi::class)
object ConductorStorageImp: ConductorStorage {

    private val logger = KotlinLogging.logger {  }
    private val pathCsv = AppConfig.dataOutput+ File.separator+"conductores.csv"
    private val pathJson = AppConfig.dataOutput+ File.separator+"conductores.json"
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()


    private val adapter = moshi.adapter<ConductorListDto>()

    override fun saveIntoJson(items: List<Conductor>): List<Conductor> {
        logger.debug { "Saving data into Json file" }
        val file = File(pathJson)
        if (!file.exists()){
            createFile(file.toPath())
        }

        file.writeText(adapter.indent("  ").toJson(items.toDtoList()))
        return items
    }

    override fun loadDatafromJson(): List<Conductor> {
        logger.debug { "Loading data from Json file" }
        val file = File(pathJson)
        if (!file.exists()){
            return emptyList()
        }

        val data = adapter.fromJson(file.readText())!!.toConductorList()
        data.forEach { println(it) }
        return data
    }

    override fun loadDataFromCsv(): List<Conductor> {
        logger.debug { "Loading data from CSV file" }
        val file = File(pathCsv)
        if (!file.exists()){
            return emptyList()
        }

        val data = file.readLines()
            .drop(1)
            .map { it.split(",") }
            .map { campos -> Conductor(
                uuid = UUID.fromString(campos[0]),
                nombre = campos[1],
                fechaCarnet = localDateFromString(campos[2])
            ) }
        data.forEach { println(it) }
        return data
    }
}
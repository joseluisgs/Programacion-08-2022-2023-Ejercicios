package services.coches

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import config.AppConfig
import dto.CocheListDto
import mapper.CocheListToDto
import mapper.toCocheList
import models.Coche
import mu.KotlinLogging
import java.io.File
import java.nio.file.Files.createFile

@OptIn(ExperimentalStdlibApi::class)
object CocheStorageImp: CocheStorage {

    private val logger = KotlinLogging.logger {  }
    private val pathCsv = AppConfig.dataOutput+ File.separator+"coches.csv"
    private val pathJson = AppConfig.dataOutput+File.separator+"coches.json"
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    private val adapter = moshi.adapter<CocheListDto>()

    override fun saveIntoJson(items: List<Coche>): List<Coche> {
        logger.debug { "Saving data into Json" }
        val file = File(pathJson)
        if (!file.exists()){
            createFile(file.toPath())
        }
        file.writeText(adapter.indent("  ").toJson(items.CocheListToDto()))
        return items
    }

    override fun loadDatafromJson(): List<Coche> {
        logger.debug { "loading data from Json" }
        val file = File(pathJson)
        if (!file.exists()){
            return emptyList()
        }
        val data = adapter.fromJson(file.readText())!!.toCocheList()
        data.forEach { println(it) }
        return data
    }

    override fun loadDataFromCsv(): List<Coche> {
        logger.debug { "loading data form csv file" }
        val file = File(pathCsv)
        if (!file.exists()){
            return emptyList()
        }
        var coches = mutableListOf<Coche>()

       val data =  file.readLines().drop(1)
            .map {
            it.split(",")
        }.map {
            campos -> Coche(
                id = campos[0].toLong(),
                marca = campos[1],
                modelo = campos[2],
                precio = campos[3].toDouble(),
                tipoMotor = enumValueOf(campos[4])
            )
        }
        data.forEach { println(it) }
        return data


    }
}
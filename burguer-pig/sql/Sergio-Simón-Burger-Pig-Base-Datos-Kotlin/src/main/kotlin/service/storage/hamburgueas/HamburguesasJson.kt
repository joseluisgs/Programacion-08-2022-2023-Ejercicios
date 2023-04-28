package service.storage.hamburgueas

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import config.AppConfig
import models.Hamburguesa
import models.Ingrediente
import mu.KotlinLogging
import utils.LocalDateTimeAdapter
import utils.UuidAdapter
import utils.toPrettyJson
import java.io.File

private val logger = KotlinLogging.logger{}

@ExperimentalStdlibApi
class HamburguesasJson: HamburguesaStorage {
    private val localFile = AppConfig.dataOutput + File.separator + "hamburguesa" + File.separator + "hamburguesa.json"

    private val moshi = Moshi.Builder()
        .add(UuidAdapter())
        .add(LocalDateTimeAdapter())
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val jsonAdapter = moshi.adapter<List<Hamburguesa>>()

    override fun saveData(items: List<Hamburguesa>) {
        logger.info { "Guardando hamburguesas en un fichero Json" }
        val file = File(localFile)
        file.writeText(jsonAdapter.toPrettyJson(items))
    }

    override fun loadData(): List<Hamburguesa> {
        logger.info { "Cargando hamburguesa desde fichero de json" }
        val file = File(localFile)
        return jsonAdapter.fromJson(file.readText()) ?: emptyList()
    }
}
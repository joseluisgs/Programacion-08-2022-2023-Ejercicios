package service.storage.ingredientes

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import config.AppConfig
import models.Hamburguesa
import models.Ingrediente
import mu.KotlinLogging
import service.base.Storage
import utils.LocalDateTimeAdapter
import utils.UuidAdapter
import utils.toPrettyJson
import java.io.File

private val logger = KotlinLogging.logger{}

@OptIn(ExperimentalStdlibApi::class)
class IngredientesJson: IngredientesStorage {
    val localFile = AppConfig.dataOutput + File.separator + "ingredientes" + File.separator +  "ingredientes.json"

    private val moshi = Moshi.Builder()
        .add(UuidAdapter())
        .add(LocalDateTimeAdapter())
        .addLast(KotlinJsonAdapterFactory())
        .build()


    private val jsonAdapter = moshi.adapter<List<Ingrediente>>()

    override fun loadData(): List<Ingrediente> {
        logger.info { "Cargando ingrediente desde fichero de json" }
        val file = File(localFile)
        return jsonAdapter.fromJson(file.readText()) ?: emptyList()
    }

    override fun saveData(data: List<Ingrediente>) {
        logger.info { "Guardando ingrediente en un fichero Json" }
        val file = File(localFile)
        file.writeText(jsonAdapter.toPrettyJson(data))
    }
}
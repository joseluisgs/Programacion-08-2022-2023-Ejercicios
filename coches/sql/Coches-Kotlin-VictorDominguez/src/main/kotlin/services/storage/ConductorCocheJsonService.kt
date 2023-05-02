package services.storage

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import models.Conductor
import mu.KotlinLogging
import services.storage.conductor.ConductorJsonService
import services.storage.conductor.ConductoresService
import utils.LocalDateAdapter
import utils.UUIDAdapter
import java.io.File

private val logger = KotlinLogging.logger {}

@OptIn(ExperimentalStdlibApi::class)
object ConductorCocheJsonService: ConductoresService {

    private val dataPath = File("${System.getProperty("user.dir")}${File.separator}data")
    private val fichero = File(dataPath, "conductores_coches.json")

    private val moshi = Moshi.Builder()
        .add(LocalDateAdapter())
        .add(UUIDAdapter())
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val jsonAdapter = moshi.adapter<List<Conductor>>()

    init {
        if (!dataPath.exists()) {
            logger.debug { "ConductorCocheJsonService -> Creando directorio DATA" }

            dataPath.mkdir()
        }
        if (!fichero.exists()) {
            logger.debug { "ConductorCocheJsonService -> Creando fichero JSON" }
            fichero.createNewFile()
        }
    }

    override fun exportar(items: List<Conductor>) {
        logger.debug { "ConductorCocheJsonService -> Exportando datos a JSON" }
        fichero.writeText(jsonAdapter.indent("  ").toJson(items))
    }

    override fun importar(): List<Conductor> {
        TODO("Not yet implemented")
    }
}
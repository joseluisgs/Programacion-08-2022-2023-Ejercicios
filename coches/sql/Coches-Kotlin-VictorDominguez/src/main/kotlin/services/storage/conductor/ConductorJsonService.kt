package services.storage.conductor

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import models.Coche
import models.Conductor
import mu.KotlinLogging
import utils.LocalDateAdapter
import utils.UUIDAdapter
import java.io.File
import kotlin.math.log

private val logger = KotlinLogging.logger{}

@OptIn(ExperimentalStdlibApi::class)
object ConductorJsonService: ConductoresService {

    private val dataPath = File("${System.getProperty("user.dir")}${File.separator}data")
    private val jsonFile = File(dataPath, "conductores.json")

    private val path = "${System.getProperty("user.dir")}${File.separator}data${File.separator}conductores.json"
    val fichero = File(path)

    private val moshi = Moshi.Builder()
        .add(LocalDateAdapter())
        .add(UUIDAdapter())
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val jsonAdapter = moshi.adapter<List<Conductor>>()

    init {
        if (!dataPath.exists()) {
            logger.debug { "ConductorJsonService -> Creando directorio DATA" }
            dataPath.mkdir()
        }
        if (!jsonFile.exists()) {
            logger.debug { "ConductorJsonService -> Creando fichero JSON" }
            jsonFile.createNewFile()
        }
    }

    override fun exportar(items: List<Conductor>) {
        logger.debug { "ConductorJsonService -> Exportando datos a JSON" }
        fichero.writeText(jsonAdapter.indent("  ").toJson(items))
    }

    override fun importar(): List<Conductor> {
        logger.debug { "ConductorJsonService -> Importando datos de JSON" }
        return jsonAdapter.fromJson(fichero.readText()) ?: emptyList()
    }

}
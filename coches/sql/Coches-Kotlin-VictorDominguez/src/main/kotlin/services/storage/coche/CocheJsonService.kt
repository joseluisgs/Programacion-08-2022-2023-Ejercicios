package services.storage.coche

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import models.Coche
import mu.KotlinLogging
import utils.LocalDateAdapter
import utils.UUIDAdapter
import java.io.File

private val logger = KotlinLogging.logger{}

@OptIn(ExperimentalStdlibApi::class)
object CocheJsonService: CochesService {

    private val dataPath = File("${System.getProperty("user.dir")}${File.separator}data")
    private val jsonFile = File(dataPath, "coches.json")

    private val path = "${System.getProperty("user.dir")}${File.separator}data${File.separator}coches.json"
    val fichero = File(path)

    private val moshi = Moshi.Builder()
        .add(LocalDateAdapter())
        .add(UUIDAdapter())
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val jsonAdapter = moshi.adapter<List<Coche>>()

    init {
        if (!dataPath.exists()) {
            logger.debug { "CocheJsonService -> Creando directorio DATA" }
            dataPath.mkdir()
        }
        if (!jsonFile.exists()) {
            logger.debug { "CocheJsonService -> Creando fichero JSON" }
            jsonFile.createNewFile()
        }
    }

    override fun exportar(items: List<Coche>) {
        logger.debug { "CocheJsonService -> Exportando datos a JSON" }
        fichero.writeText(jsonAdapter.indent("  ").toJson(items))
    }

    override fun importar(): List<Coche> {
        logger.debug { "CocheJsonService -> Importando datos de JSON" }
        return jsonAdapter.fromJson(fichero.readText()) ?: emptyList()
    }


}
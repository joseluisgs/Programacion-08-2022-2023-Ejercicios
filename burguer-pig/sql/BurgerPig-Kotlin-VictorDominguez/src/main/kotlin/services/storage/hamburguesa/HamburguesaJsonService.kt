package services.storage.hamburguesa

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dto.HamburguesaListDto
import mappers.toHamburguesaList
import mappers.toHamburguesaListDto
import models.Hamburguesa
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger{}

@OptIn(ExperimentalStdlibApi::class)
object HamburguesaJsonService: HamburguesaService {

    private val dataPath = File("${System.getProperty("user.dir")}${File.separator}data")
    private val fichero = File(dataPath, "hamburguesas.json")

    private val adapter = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter<HamburguesaListDto>()

    init {
        if (!dataPath.exists()) {
            logger.debug { "HamburguesaJsonService -> Creando directorio DATA" }
            dataPath.mkdir()
        }
        if (!fichero.exists()) {
            logger.debug { "HamburguesaJsonService -> Creando fichero JSON" }
            fichero.createNewFile()
        }
    }

    override fun exportar(items: List<Hamburguesa>) {
        logger.debug { "HamburguesaJsonService -> Exportando datos a JSON" }
        fichero.writeText(adapter.indent("  ").toJson(items.toHamburguesaListDto()))
    }

    override fun importar(): List<Hamburguesa> {
        logger.debug { "HamburguesaJsonService -> Importando datos de JSON" }
        return adapter.fromJson(fichero.readText())?.toHamburguesaList() ?: emptyList()
    }
}
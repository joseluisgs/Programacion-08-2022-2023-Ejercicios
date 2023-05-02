package services.storage

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dto.HamburguesaListDto
import mappers.toHamburguesaListDto
import models.Hamburguesa
import mu.KotlinLogging
import services.storage.hamburguesa.HamburguesaService
import java.io.File

private val logger = KotlinLogging.logger{}

@OptIn(ExperimentalStdlibApi::class)
object HamburguesasIngredientesJsonService: HamburguesaService {

    private val dataPath = File("${System.getProperty("user.dir")}${File.separator}data")
    private val fichero = File(dataPath, "hamburguesas_ingredientes.json")

    private val adapter = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter<HamburguesaListDto>()

    init {
        if (!dataPath.exists()) {
            logger.debug { "HamburguesasIngredientesJsonService -> Creando directorio DATA" }
            dataPath.mkdir()
        }
        if (!fichero.exists()) {
            logger.debug { "HamburguesasIngredientesJsonService -> Creando fichero JSON" }
            fichero.createNewFile()
        }
    }

    override fun exportar(items: List<Hamburguesa>) {
        logger.debug { "HamburguesasIngredientesJsonService -> Exportando datos a JSON" }
        fichero.writeText(adapter.indent("  ").toJson(items.toHamburguesaListDto()))
    }

    override fun importar(): List<Hamburguesa> {
        TODO("Not yet implemented")
    }
}
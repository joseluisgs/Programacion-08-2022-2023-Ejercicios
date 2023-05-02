package services.storage.ingrediente

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dto.IngredienteListDto
import mappers.toIngredienteList
import mappers.toIngredienteListDto
import models.Ingrediente
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger{}

@OptIn(ExperimentalStdlibApi::class)
object IngredienteJsonService: IngredienteService {

    private val dataPath = File("${System.getProperty("user.dir")}${File.separator}data")
    private val fichero = File(dataPath, "ingredientes.json")
    private val adapter = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter<IngredienteListDto>()

    init {
        if (!dataPath.exists()) {
            logger.debug { "IngredienteJsonService -> Creando directorio DATA" }
            dataPath.mkdir()
        }
        if (!fichero.exists()) {
            logger.debug { "IngredienteJsonService -> Creando fichero JSON" }
            fichero.createNewFile()
        }
    }

    override fun exportar(items: List<Ingrediente>) {
        logger.debug { "IngredienteJsonService -> Exportando datos a JSON" }
        fichero.writeText(adapter.indent("  ").toJson(items.toIngredienteListDto()))
    }

    override fun importar(): List<Ingrediente> {
        logger.debug { "IngredienteJsonService -> Importando datos de JSON" }
        return adapter.fromJson(fichero.readText())?.toIngredienteList() ?: emptyList()
    }
}
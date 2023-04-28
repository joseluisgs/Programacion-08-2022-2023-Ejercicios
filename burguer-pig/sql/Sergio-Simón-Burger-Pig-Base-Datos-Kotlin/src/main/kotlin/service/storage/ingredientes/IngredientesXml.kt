package service.storage.ingredientes

import config.AppConfig
import mapper.toDto
import mapper.toHamburguesaList
import mapper.toIngredienteList
import models.Ingrediente
import mu.KotlinLogging
import org.simpleframework.xml.core.Persister
import java.io.File

private val logger = KotlinLogging.logger {}

class IngredientesXml: IngredientesStorage {
    val localFile = AppConfig.dataOutput + File.separator + "ingredientes" + File.separator +  "ingredientes.xml"

    private val serializer = Persister()

    override fun loadData(): List<Ingrediente> {
        logger.info { "Cargando hamburguesas desde un fichero de xml" }
        val file = File(localFile)
        return serializer.read(dto.ListIngredientesDto::class.java, file).toIngredienteList()
    }

    override fun saveData(data: List<Ingrediente>) {
        logger.info { "Guardando hamburguesas en un fichero de xml" }
        val file = File(localFile)
        serializer.write(data.toDto(), file)
    }
}
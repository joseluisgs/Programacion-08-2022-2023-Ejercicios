package service.storage.hamburgueas

import config.AppConfig
import mapper.toDto
import mapper.toHamburguesaList
import models.Hamburguesa
import mu.KotlinLogging
import org.simpleframework.xml.core.Persister
import java.io.File

private val logger = KotlinLogging.logger {}

class HamburguesasXml: HamburguesaStorage {
    private val localFile = AppConfig.dataOutput + File.separator + "hamburguesa" + File.separator + "hamburguesa.xml"

    private val serializer = Persister()

    override fun loadData(): List<Hamburguesa> {
        logger.info { "Cargando hamburguesas desde un fichero de xml" }
        val file = File(localFile)
        return serializer.read(dto.HamburguesasListDto::class.java, file).toHamburguesaList()
    }

    override fun saveData(data: List<Hamburguesa>) {
        logger.info { "Guardando hamburguesas en un fichero de xml" }
        val file = File(localFile)
        serializer.write(data.toDto(), file)
    }
}
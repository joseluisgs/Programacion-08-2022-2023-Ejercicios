package service.storageService.Hamburger

import config.ConfigApp
import dto.hamburgesa.ListaHamburgesasDto
import mapper.toDtos
import mapper.toHamburgesas
import model.Hamburgesa
import mu.KotlinLogging
import org.simpleframework.xml.core.Persister
import storageService.Hamburger.HamburgesaStorageService
import java.io.File

class HamburgesasStorageServiceXml(
    private val configApp: ConfigApp
): HamburgesaStorageService {

    private val logger = KotlinLogging.logger {  }

    private val file = File(configApp.APP_DATA+File.separator+"hamburgesas.xml")

    private val persister = Persister()

    override fun saveAll(entities: List<Hamburgesa>) {
        logger.debug { "Guardo toda la info en el fichro XML" }

        persister.write(entities.toDtos(), file)
    }

    override fun loadAll(): List<Hamburgesa> {
        logger.debug { "Consigo toda la info del fichero XML" }

        if(!file.exists()) return emptyList()
        return persister.read(ListaHamburgesasDto::class.java, file).toHamburgesas()
    }
}
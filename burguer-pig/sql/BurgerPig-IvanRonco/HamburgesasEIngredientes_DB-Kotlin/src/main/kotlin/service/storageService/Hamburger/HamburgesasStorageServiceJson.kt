package service.storageService.Hamburger

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import config.ConfigApp
import dto.hamburgesa.ListaHamburgesasDto
import mapper.toDtos
import mapper.toHamburgesas
import model.Hamburgesa
import mu.KotlinLogging
import storageService.Hamburger.HamburgesaStorageService
import java.io.File

@ExperimentalStdlibApi
class HamburgesasStorageServiceJson(
    private val configApp: ConfigApp
): HamburgesaStorageService {

    private val logger = KotlinLogging.logger {  }

    private val file = File(configApp.APP_DATA+ File.separator+"hamburgesas.json")

    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val adapter = moshi.adapter<ListaHamburgesasDto>()

    override fun saveAll(entities: List<Hamburgesa>) {
        logger.debug { "Guardo toda la info en el fichero JSON" }

        file.writeText(
            adapter.indent("   ").toJson(entities.toDtos())
        )
    }

    override fun loadAll(): List<Hamburgesa> {
        logger.debug { "Consigo toda la info del fichero JSON" }

        if(!file.exists()) return emptyList()
        return adapter.fromJson(file.readText())!!.toHamburgesas()
    }
}
package service.storageService.Hamburger

import config.ConfigApp
import model.Hamburgesa
import model.Ingrediente
import mu.KotlinLogging
import storageService.Hamburger.HamburgesaStorageService
import java.io.File
import java.util.*

class HamburgesasStorageServiceCsv(
    private val configApp: ConfigApp
): HamburgesaStorageService {

    private val logger = KotlinLogging.logger {  }

    private val file = File(configApp.APP_DATA+ File.separator+"hamburgesas.csv")

    override fun saveAll(entities: List<Hamburgesa>) {
        logger.debug { "Guardo toda la info en el fichero CSV" }

        file.writeText("id;nombre;ingredientes"+"\n")
        entities.forEach {
            file.appendText("${it.id};${it.nombre};${it.ingredientes.toCsv()}"+"\n")
        }
    }

    override fun loadAll(): List<Hamburgesa> {
        logger.debug { "Consigo toda la info del fichero CSV" }

        if(!file.exists()) return emptyList()
        return file.readLines().drop(1)
            .map { it.split(";") }
            .map {  it.map { it.trim() } }
            .map {
                Hamburgesa(
                    id = UUID.fromString(it[0]),
                    nombre =  it[1],
                    ingredientes = it[2].fromCsv(),
                )
            }
    }
}

private fun String.fromCsv(): MutableList<Ingrediente> {
    return this.split("|").map { it.split(",") }
        .map { it.map { it.trim() } }
        .map {
            Ingrediente(
                id = it[0].toLong(),
                nombre = it[1],
                precio = it[2].toDouble()
            )
        }.toMutableList()
}

private fun  List<Ingrediente>.toCsv(): String {
    return this.joinToString(separator = "|") {
        "${it.id},${it.nombre},${it.precio}"
    }
}

package service.storage.profesores

import config.ConfigApp
import models.Profesor
import mu.KotlinLogging
import java.io.File
import java.util.*

class ProfesorStorageServiceCsv: ProfesorStorageService {

    val config = ConfigApp
    val file = File(config.APP_DATA+File.separator+"profesores.csv")

    val logger = KotlinLogging.logger {  }

    override fun safeAll(entites: List<Profesor>) {
        logger.debug { "Se guardan todos los profesores" }
        file.writeText("uuid;nombre;experiencia"+"\n")
        entites.forEach {
            file.appendText("${it.uuid};${it.nombre};${it.experiencia}"+"\n")
        }
    }

    override fun loadAll(): List<Profesor> {
        logger.debug { "Se cargan todos los profesores" }
        if(!file.exists()) return emptyList()
        return file.readLines().drop(1).map { it.split(";") }.map { it.map { it.trim() } }
            .map {
                Profesor(
                    uuid = UUID.fromString(it[0]),
                    nombre = it[1],
                    experiencia = it[2].toInt()
                )
            }
    }
}
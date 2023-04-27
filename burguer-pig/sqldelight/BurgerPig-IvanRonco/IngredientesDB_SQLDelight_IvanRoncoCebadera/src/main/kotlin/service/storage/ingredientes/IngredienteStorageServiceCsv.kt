package service.storage.ingredientes

import config.ConfigApp
import factory.IngredientesFactory
import models.Ingrediente
import mu.KotlinLogging
import java.io.File
import java.time.LocalDate
import java.util.*

class IngredienteStorageServiceCsv: IngredienteStorageService {

    val config = ConfigApp
    val file = File(config.APP_DATA+File.separator+"ingredientes.csv")

    val logger = KotlinLogging.logger {  }

    override fun safeAll(entites: List<Ingrediente>) {
        logger.debug { "Se guardan todos los ingredientes" }
        file.writeText("uuid;nombre;precio;cantidad;createdAt;updatedAt;disponible"+"\n")
        entites.forEach {
            file.appendText("${it.uuid};${it.nombre};${it.precio};${it.cantidad};${it.createAt};${it.updatedAt};${if(it.disponible) 1 else 0}"+"\n")
        }
    }

    override fun loadAll(): List<Ingrediente> {
        logger.debug { "Se cargan todos los ingredientes" }
        if(!file.exists()) return emptyList()
        return file.readLines().drop(1).map { it.split(";") }.map { it.map { it.trim() } }
            .map {
                Ingrediente(
                    uuid = UUID.fromString(it[0]),
                    nombre = it[1],
                    precio = it[2].toDouble(),
                    cantidad = it[3].toInt(),
                    createAt = LocalDate.parse(it[4]),
                    updatedAt = LocalDate.parse(it[5]),
                    disponible = it[6] == "1"
                )
            }
    }
}
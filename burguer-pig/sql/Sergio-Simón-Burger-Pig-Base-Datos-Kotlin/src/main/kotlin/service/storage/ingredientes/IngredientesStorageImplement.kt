package service.storage.ingredientes

import config.AppConfig
import models.Ingrediente
import mu.KotlinLogging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.util.*

private val logger = KotlinLogging.logger {}

class IngredientesStorageImplement: IngredientesStorage {

    override fun loadData(): List<Ingrediente> {
        logger.debug { "Load ingredientes in a csv file" }
        val file = File(AppConfig.dataOutput + File.separator + "ingredientes" + File.separator +  "ingredientes.csv")
        val ingredients = mutableListOf<Ingrediente>()

        if (!file.exists()) {
            logger.error { "File does not exist" }
            return listOf()
        }

        return file.readLines()
            .drop(1)
            .map { it.split(",") }
            .map {
                it.map { items -> items.trim() }
            }
            .map {
                Ingrediente(
                    id = it[0].toLong(),
                    uuid = UUID.fromString(it[1]),
                    name = it[2],
                    price = it[3].toDouble(),
                    disponible = it[4].toBoolean(),
                    cantidad = it[5].toInt(),
                    createdAt = LocalDateTime.parse(it[6]),
                    updatedAt = LocalDateTime.parse(it[7])
                )
            }

    }

    override fun saveData(data: List<Ingrediente>) {
        logger.debug { "Saving $data in a csv file" }
        val file = File(AppConfig.dataOutput + File.separator + "ingredientes" + File.separator +  "ingredientes.csv")

        file.writeText("id, uuid, name, price, disponible, cantidad,createAt, updateAt\n")
        data.forEach {
            file.appendText("${it.id}, ${it.uuid}, ${it.name}, ${it.price}, ${it.disponible}, ${it.cantidad},${it.createdAt}, ${it.updatedAt}\n")
        }
    }
}
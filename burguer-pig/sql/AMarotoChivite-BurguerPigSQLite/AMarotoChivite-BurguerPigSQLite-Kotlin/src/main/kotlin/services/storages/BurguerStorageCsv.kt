package services.storages

import config.ConfigApp
import models.Burguer
import models.LineaBurguer
import services.storages.base.IStorageGeneral
import java.io.File
import java.util.UUID

private val logger = mu.KotlinLogging.logger {}

open class BurguerStorageCsv : IStorageGeneral<Burguer> {

    override fun writeFile(data: List<Burguer>): Boolean {
        logger.debug { "Storage: Escribiendo en CSV" }

        val localFileToWrite = ConfigApp.getPathDataOutput("csv")
        val fileToWrite = File(localFileToWrite)

        fileToWrite.writeText("uuid_burguer,name_burguer,stock_burguer;linea_id;uuid_burguer;ingrediente_id;ingrediente_quantity;ingrediente_price" + "\n")
        data.forEach {
            fileToWrite.appendText("${it.uuid},${it.name},${it.stock},${writeEmbeddedList(it.lineaBurguer!!)} " + "\n")
        }

        return true
    }

    private fun writeEmbeddedList(lines: List<LineaBurguer>): String {
        return lines.joinToString("|") {
            "${it.lineaId};${it.burguerUUID};${it.ingredienteId};${it.ingredienteQuantity};${it.ingredientePrice}"
        }
    }


    override fun readFile(): List<Burguer> {
        logger.debug { "Storage: Leyendo desde fichero CSV" }

        val localFileToRead = ConfigApp.getPathDataInput("csv")
        val fileToRead = File(localFileToRead)

        if (!fileToRead.exists()) {
            throw IllegalAccessError("El fichero no existe")
        }
        if (!fileToRead.canRead()) {
            throw IllegalAccessError("El fichero no tiene permisos de lectura")
        }

        // Leer el fichero completo y eliminamos la primera fila del encabezado
        val lines = fileToRead.readLines().drop(1)

        return lines.map { line ->
            val fields = line.split(',')

            val uuid = UUID.fromString(fields[0])
            val name = fields[1]
            val stock = fields[2].toInt()
            val lineasBurguer = fields[3].split('|')
            val lineas = lineasBurguer.map { linea ->
                val lineaField = linea.split(';')
                val id = lineaField[0].toLong()
                val burguerUuid = UUID.fromString(lineaField[1])
                val ingredienteId = lineaField[2].toLong()
                val ingredienteQuantity = lineaField[3].toInt()
                val ingredientePrice = lineaField[4].toDouble()

                LineaBurguer(id, burguerUuid, ingredienteId, ingredienteQuantity,ingredientePrice)
            }

            Burguer(uuid, name,stock, lineas.toMutableList())
        }
    }
}
package services.storages


import config.ConfigApp
import models.Modulo
import services.storages.base.IStorageGeneral
import utils.typeGrade
import java.io.File
import java.util.*

private val logger = mu.KotlinLogging.logger {}

open class ModuloStorageCsv : IStorageGeneral<Modulo> {

    override fun writeFile(data: List<Modulo>): Boolean {
        logger.debug { "Storage: Escribiendo en CSV" }

        val localFileToWrite = ConfigApp.getPathDataOutput("Modulo","csv")
        val fileToWrite = File(localFileToWrite)

        fileToWrite.writeText("uuid_modulo,name_modulo,curso_modulo,grado_modulo" + "\n")
        data.forEach {
            fileToWrite.appendText("${it.uuid},${it.name},${it.curso},${it.grado}" + "\n")
        }

        return true
    }


    override fun readFile(): List<Modulo> {
        logger.debug { "Storage: Leyendo desde fichero CSV" }

        val localFileToRead = ConfigApp.getPathDataInput("Modulo","csv")
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
            val curso = fields[2].toInt()
            val grado = fields[3]

            Modulo(uuid, name,curso, typeGrade(grado))
        }
    }

}
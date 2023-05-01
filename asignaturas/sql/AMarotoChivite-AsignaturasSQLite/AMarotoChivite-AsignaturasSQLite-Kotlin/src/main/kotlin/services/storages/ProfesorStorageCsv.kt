package services.storages

import config.ConfigApp
import models.Profesor
import services.storages.base.IStorageGeneral
import java.io.File
import java.time.LocalDate

private val logger = mu.KotlinLogging.logger {}

open class ProfesorStorageCsv : IStorageGeneral<Profesor> {

    override fun writeFile(data: List<Profesor>): Boolean {
        logger.debug { "Storage: Escribiendo en CSV" }

        val localFileToWrite = ConfigApp.getPathDataOutput("Profesor","csv")
        val fileToWrite = File(localFileToWrite)

        fileToWrite.writeText("id_profesor,name_profesor,fecha_ingreso" + "\n")
        data.forEach {
            fileToWrite.appendText("${it.id},${it.name},${it.dateInit}" + "\n")
        }
        return true
    }


    override fun readFile(): List<Profesor> {
        logger.debug { "Storage: Leyendo desde fichero CSV" }

        val localFileToRead = ConfigApp.getPathDataInput("Profesor","csv")
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

            val id = fields[0].toLong()
            val name = fields[1]
            val dateInit = LocalDate.parse(fields[2])

            Profesor(id, name,dateInit)
        }
    }

}
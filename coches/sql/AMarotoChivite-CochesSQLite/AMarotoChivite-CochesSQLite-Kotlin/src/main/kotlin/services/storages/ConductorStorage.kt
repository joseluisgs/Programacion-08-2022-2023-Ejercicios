package services.storages

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import config.ConfigApp
import models.Conductor
import models.dto.ConductorListDto
import org.simpleframework.xml.core.Persister
import services.storages.base.IStorageGeneral
import utils.mappers.ConductorListMapper
import java.io.File
import java.util.*

private val logger = mu.KotlinLogging.logger {}

open class ConductorStorage : IStorageGeneral<Conductor> {

    // Para XML
    private val serializer = Persister()

    override fun writeFileToJson(data: List<Conductor>): Boolean {
        logger.debug { "Storage: Escribiendo en JSON" }

        val localFileToWrite = ConfigApp.getPathDataOutput("json")
        val fileToWrite = File(localFileToWrite)

        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonString = gson.toJson(data)
        fileToWrite.writeText(jsonString)

        return true
    }

    override fun writeFileToXml(data: List<Conductor>): Boolean {
        logger.debug { "Storage: Escribiendo en XML" }

        val localFileToWrite = ConfigApp.getPathDataOutput("xml")
        val fileToWrite = File(localFileToWrite)

        val lisTDto = ConductorListMapper().toDto(data).list
        val objectList = ConductorListDto()
        objectList.dtoList = lisTDto
        serializer.write(objectList, fileToWrite)

        return true
    }

    override fun writeFileToCsv(data: List<Conductor>): Boolean {
        logger.debug { "Storage: Escribiendo en CSV" }

        val localFileToWrite = ConfigApp.getPathDataOutput("csv")
        val fileToWrite = File(localFileToWrite)

        fileToWrite.writeText("uuid,name" + "\n")
        data.forEach {
            fileToWrite.appendText("${it.uuid},${it.name}" + "\n")
        }

        return true
    }


    override fun readFileOfJson(): List<Conductor> {
        logger.debug { "Storage: Leyendo desde JSON" }

        val localFileToRead = ConfigApp.getPathDataInput("json")
        val fileToRead = File(localFileToRead)

        if (!fileToRead.exists()) {
            throw IllegalAccessError("El fichero no existe")
        }
        if (!fileToRead.canRead()) {
            throw IllegalAccessError("El fichero no tiene permisos de lectura")
        }

        val gson = Gson()
        val jsonString = fileToRead.readText()
        val type = object : TypeToken<List<Conductor>>() {}.type
        return gson.fromJson(jsonString, type)
    }

    override fun readFileOfCsv(): List<Conductor> {
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

            Conductor(uuid, name)
        }
    }

    override fun readFileOfXml(): List<Conductor> {
        logger.debug { "Storage: Leyendo desde XML" }

        val localFileToRead = ConfigApp.getPathDataInput("xml")
        val fileToRead = File(localFileToRead)

        if (!fileToRead.exists()) {
            throw IllegalAccessError("El fichero no existe")
        }
        if (!fileToRead.canRead()) {
            throw IllegalAccessError("El fichero no tiene permisos de lectura")
        }

        val objectListDto = serializer.read(ConductorListDto::class.java, fileToRead)
        return ConductorListMapper().toModelList(objectListDto)
    }

}
package services.storages

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import config.ConfigApp
import models.Vehicle
import models.dto.VehicleListDto
import org.simpleframework.xml.core.Persister
import services.storages.base.IStorageGeneral
import utils.getTypeMotor
import utils.mappers.VehicleListMapper
import java.io.File
import java.util.*

private val logger = mu.KotlinLogging.logger {}

class VehicleStorage : IStorageGeneral<Vehicle> {

    // Para XML
    private val serializer = Persister()

    override fun writeFileToJson(data: List<Vehicle>): Boolean {
        logger.debug { "Storage: Escribiendo en JSON" }

        val localFileToWrite = ConfigApp.getPathDataOutput("json")
        val fileToWrite = File(localFileToWrite)

        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonString = gson.toJson(data)
        fileToWrite.writeText(jsonString)

        return true
    }

    override fun writeFileToXml(data: List<Vehicle>): Boolean {
        logger.debug { "Storage: Escribiendo en XML" }

        val localFileToWrite = ConfigApp.getPathDataOutput("xml")
        val fileToWrite = File(localFileToWrite)

        val lisTDto = VehicleListMapper().toDto(data).list
        val objectList = VehicleListDto()
        objectList.dtoList = lisTDto
        serializer.write(objectList, fileToWrite)

        return true
    }

    override fun writeFileToCsv(data: List<Vehicle>): Boolean {
        logger.debug { "Storage: Escribiendo en CSV" }

        val localFileToWrite = ConfigApp.getPathDataOutput("csv")
        val fileToWrite = File(localFileToWrite)

        fileToWrite.writeText("uuid,model,motor,fk_uuid_conductor" + "\n")
        data.forEach {
            fileToWrite.appendText("${it.uuid},${it.model},${it.motor},${it.foreignUuidConductor}" + "\n")
        }

        return true
    }


    override fun readFileOfJson(): List<Vehicle> {
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
        val type = object : TypeToken<List<Vehicle>>() {}.type
        return gson.fromJson(jsonString, type)
    }

    override fun readFileOfCsv(): List<Vehicle> {
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
            val model = fields[1]
            val motor = getTypeMotor(fields[2])
            val foreignUuid = UUID.fromString(fields[3])

            Vehicle(uuid, model, motor, foreignUuid)
        }
    }

    override fun readFileOfXml(): List<Vehicle> {
        logger.debug { "Storage: Leyendo desde XML" }

        val localFileToRead = ConfigApp.getPathDataInput("xml")
        val fileToRead = File(localFileToRead)

        if (!fileToRead.exists()) {
            throw IllegalAccessError("El fichero no existe")
        }
        if (!fileToRead.canRead()) {
            throw IllegalAccessError("El fichero no tiene permisos de lectura")
        }

        val objectListDto = serializer.read(VehicleListDto::class.java, fileToRead)
        return VehicleListMapper().toModelList(objectListDto)
    }
}
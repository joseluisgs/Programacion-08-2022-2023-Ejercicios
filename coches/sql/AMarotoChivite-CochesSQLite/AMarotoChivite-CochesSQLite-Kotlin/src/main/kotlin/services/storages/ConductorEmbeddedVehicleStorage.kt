package services.storages

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import config.ConfigApp
import models.dto.ConductorEmbeddedDto
import models.dto.ConductorEmbeddedListDto
import models.dto.VehicleDto
import org.simpleframework.xml.core.Persister
import services.storages.base.IStorageGeneral
import java.io.File

private val logger = mu.KotlinLogging.logger {}

open class ConductorEmbeddedVehicleStorage : IStorageGeneral<ConductorEmbeddedDto> {

    // Para XML
    private val serializer = Persister()

    override fun writeFileToJson(data: List<ConductorEmbeddedDto>): Boolean {
        logger.debug { "Storage: Escribiendo en JSON" }

        val localFileToWrite = ConfigApp.getPathDataOutput("json")
        val fileToWrite = File(localFileToWrite)

        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonString = gson.toJson(data)
        fileToWrite.writeText(jsonString)

        return true
    }

    override fun writeFileToXml(data: List<ConductorEmbeddedDto>): Boolean {
        logger.debug { "Storage: Escribiendo en XML" }

        val localFileToWrite = ConfigApp.getPathDataOutput("xml")
        val fileToWrite = File(localFileToWrite)

        val objectList = ConductorEmbeddedListDto()
        objectList.dtoList = data

        serializer.write(objectList, fileToWrite)

        return true
    }

    override fun writeFileToCsv(data: List<ConductorEmbeddedDto>): Boolean {
        logger.debug { "Storage: Escribiendo en CSV" }

        val localFileToWrite = ConfigApp.getPathDataOutput("csv")
        val fileToWrite = File(localFileToWrite)

        fileToWrite.writeText("uuid_conductor,name_conductor,uuid_vehicle;model_vehicle;motor_vehicle;uuid_conductor_fk" + "\n")
        data.forEach {
            fileToWrite.appendText("${it.uuid},${it.name},${writeEmbedded(it.listVehicle!!)} " + "\n")
        }

        return true
    }

    private fun writeEmbedded(vehicles: List<VehicleDto>): String {
        return vehicles.joinToString("|") {
            "${it.uuid};${it.model};${it.motor};${it.foreignUuidConductor}"
        }
    }

    override fun readFileOfJson(): List<ConductorEmbeddedDto> {
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
        val type = object : TypeToken<List<ConductorEmbeddedDto>>() {}.type
        return gson.fromJson(jsonString, type)
    }

    override fun readFileOfCsv(): List<ConductorEmbeddedDto> {
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

            val uuid = fields[0]
            val name = fields[1]
            val vehiclesToFields = fields[2].split('|')
            val vehicles = vehiclesToFields.map { vehicle ->
                val vehicleFields = vehicle.split(';')
                val uuid = vehicleFields[0]
                val model = vehicleFields[1]
                val motor = vehicleFields[2]
                val uuidFK = vehicleFields[3]
                VehicleDto(uuid, model, motor, uuidFK)
            }

            ConductorEmbeddedDto(uuid, name, vehicles)
        }
    }

    override fun readFileOfXml(): List<ConductorEmbeddedDto>? {
        logger.debug { "Storage: Leyendo desde XML" }

        val localFileToRead = ConfigApp.getPathDataInput("xml")
        val fileToRead = File(localFileToRead)

        if (!fileToRead.exists()) {
            throw IllegalAccessError("El fichero no existe")
        }
        if (!fileToRead.canRead()) {
            throw IllegalAccessError("El fichero no tiene permisos de lectura")
        }

        val objectListDto = serializer.read(ConductorEmbeddedListDto::class.java, fileToRead)
        return objectListDto.dtoList
    }

}
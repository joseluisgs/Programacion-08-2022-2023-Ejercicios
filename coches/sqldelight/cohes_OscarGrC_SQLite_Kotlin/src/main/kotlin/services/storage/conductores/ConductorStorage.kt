package services.storage.conductores

import Dto.ConductorDTO
import Dto.ConductorDTOList
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import config.AppConflig
import mappers.toDTO
import models.Conductor
import mu.KotlinLogging
import org.simpleframework.xml.core.Persister
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.util.*

object ConductorStorage: IConductorStorage {
    private val logger= KotlinLogging.logger {  }

        override fun exportToCsv(data: List<Conductor>) {
            logger.debug { "Creando directorio ${AppConflig.outputData}" }
            // Creamos el directorio data si no existe
            Files.createDirectories(Paths.get(AppConflig.outputData))
            val file = File(AppConflig.outputData + File.separator + "Conductor.csv")
            if(!file.exists())file.createNewFile()
            logger.debug { "Saving directorio ${file.name}" }
            file.writeText("UUID,nombre,FechaCarnet\n")
            data.forEach {
               file.appendText("${it.uuid},${it.nombre},${it.fechaCarnet}\n")
            }}
        @OptIn(ExperimentalStdlibApi::class)
        override fun exportToJson(data: List<Conductor>) {
            logger.debug { "Exportando a JSON" }
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter<List<ConductorDTO>>()
            var listaDto:MutableList<ConductorDTO> = mutableListOf()
            data.forEach { listaDto+=it.toDTO() }
            val json = jsonAdapter.indent("   ").toJson(listaDto)
            val file = File(AppConflig.outputData + File.separator +"Conductor.json")
            if (!file.exists()) {
                file.createNewFile()
            }
            file.delete()
            file.writeText(json)
            println("JSON guardado en ${file.absolutePath}")
        }
        override fun exportToXml(data: List<Conductor>) {
            logger.debug { "Exportando a XML" }
            val file = File(AppConflig.outputData+ File.separator+"Conductor.xml")
            if (!file.exists()) {
                file.createNewFile()
            }
            val serializer = Persister()
            var listaDTO:MutableList<ConductorDTO> = mutableListOf()
            data.forEach { listaDTO+= it.toDTO() }
            val escritura: ConductorDTOList = ConductorDTOList(listaDTO)
            serializer.write(escritura, file)
            println("XML guardado en ${file.absolutePath}")
        }
        override fun loadData(): List<Conductor> {
            logger.debug { "Creando directorio ${AppConflig.inputData}" }
            // ¿Existe el fichero???
            val file = File(AppConflig.outputData + File.separator + "Conductor.csv")
            if (!file.exists()) {
               file.createNewFile()
            }
            return file.readLines().drop(1)
                .map {fila ->
                    fila.split(",")
                }.map { columnas ->
                        Conductor(
                            uuid= UUID.fromString(columnas[0]),
                            nombre = columnas[1],
                            fechaCarnet = LocalDate.parse(columnas[2]))
                }.toMutableList()
        }
        @OptIn(ExperimentalStdlibApi::class)
        override fun saveData(data: List<Conductor>): Boolean {
            logger.debug { "save Data" }
            logger.debug { "Creando directorio ${AppConflig.outputData}" }
            Files.createDirectories(Paths.get(AppConflig.outputData))
            val file = File(AppConflig.outputData + File.separator + "Conductor.json")
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter<List<ConductorDTO>>()
            file.writeText(
                jsonAdapter.indent("    ").toJson(data.map { it.toDTO() })
            )
            return true
        }
    }
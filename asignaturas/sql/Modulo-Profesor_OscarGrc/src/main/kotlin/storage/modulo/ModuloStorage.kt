package storage.modulo

import Dto.ModuloDTO
import Dto.ModulosDTOList
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import config.AppConflig
import mappers.toDTO
import models.Grado
import models.Modulo
import mu.KotlinLogging
import org.simpleframework.xml.Element
import org.simpleframework.xml.core.Persister
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.util.*

object ModuloStorage: IModuloStorage {
    private val logger= KotlinLogging.logger {  }

        override fun exportToCsv(data: List<Modulo>) {
            logger.debug { "Creando directorio ${AppConflig.outputData}" }
            // Creamos el directorio data si no existe
            Files.createDirectories(Paths.get(AppConflig.outputData))
            val file = File(AppConflig.outputData + File.separator + "Modulo.csv")
            if(!file.exists())file.createNewFile()
            logger.debug { "Saving directorio ${file.name}" }
            file.writeText("UUID,nombre,curso,grado\n")
            data.forEach {
               file.appendText("${it.uuid},${it.nombre},${it.curso},${it.grado.name}\n")
            }}
        @OptIn(ExperimentalStdlibApi::class)
        override fun exportToJson(data: List<Modulo>) {
            logger.debug { "Exportando a JSON" }
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter<List<ModuloDTO>>()
            var listaDto:MutableList<ModuloDTO> = mutableListOf()
            data.forEach { listaDto+=it.toDTO() }
            val json = jsonAdapter.indent("   ").toJson(listaDto)
            val file = File(AppConflig.outputData + File.separator +"Modulo.json")
            if (!file.exists()) {
                file.createNewFile()
            }
            file.delete()
            file.writeText(json)
            println("JSON guardado en ${file.absolutePath}")
        }
        override fun exportToXml(data: List<Modulo>) {
            logger.debug { "Exportando a XML" }
            val file = File(AppConflig.outputData+ File.separator+"Modulo.xml")
            if (!file.exists()) {
                file.createNewFile()
            }
            val serializer = Persister()
            var listaDTO:MutableList<ModuloDTO> = mutableListOf()
            data.forEach { listaDTO+= it.toDTO() }
            val escritura: ModulosDTOList = ModulosDTOList(listaDTO)
            serializer.write(escritura, file)
            println("XML guardado en ${file.absolutePath}")
        }
        override fun loadData(): List<Modulo> {
            logger.debug { "Creando directorio ${AppConflig.inputData}" }
            // ¿Existe el fichero???
            val file = File(AppConflig.outputData + File.separator + "Modulo.csv")
            if (!file.exists()) {
               file.createNewFile()
            }
            return file.readLines().drop(1)
                .map {fila ->
                    fila.split(",")
                }.map { columnas ->
                    Modulo(
                            uuid= UUID.fromString(columnas[0]),
                            nombre = columnas[1],
                            curso = columnas[2],
                            grado = Grado.valueOf(columnas[3])
                    )
                }.toMutableList()
        }
        @OptIn(ExperimentalStdlibApi::class)
        override fun saveData(data: List<Modulo>): Boolean {
            logger.debug { "save Data" }
            logger.debug { "Creando directorio ${AppConflig.outputData}" }
            Files.createDirectories(Paths.get(AppConflig.outputData))
            val file = File(AppConflig.outputData + File.separator + "Modulo.json")
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter<List<ModuloDTO>>()
            file.writeText(
                jsonAdapter.indent("    ").toJson(data.map { it.toDTO() })
            )
            return true
        }
    }
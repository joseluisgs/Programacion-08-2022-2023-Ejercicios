package storage.profesor

import Dto.ProfesorDTO
import Dto.ProfesoresDTOList
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import config.AppConflig
import mappers.toDTO
import models.Profesor
import mu.KotlinLogging
import org.simpleframework.xml.core.Persister
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate

object ProfesorStorage: IProfesorStorage {
    private val logger= KotlinLogging.logger {  }

        override fun exportToCsv(data: List<Profesor>) {
            logger.debug { "Creando directorio ${AppConflig.outputData}" }
            // Creamos el directorio data si no existe
            Files.createDirectories(Paths.get(AppConflig.outputData))
            val file = File(AppConflig.outputData + File.separator + "Profesor.csv")
            if(!file.exists())file.createNewFile()
            logger.debug { "Saving directorio ${file.name}" }
            file.writeText("ID,nombre,fechaIncorporacion\n")
            data.forEach {
               file.appendText("${it.id},${it.nombre},${it.fehcaIncorpracion}\n")
            }}
        @OptIn(ExperimentalStdlibApi::class)
        override fun exportToJson(data: List<Profesor>) {
            logger.debug { "Exportando a JSON" }
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter<List<ProfesorDTO>>()
            var listaDto:MutableList<ProfesorDTO> = mutableListOf()
            data.forEach { listaDto+=it.toDTO() }
            val json = jsonAdapter.indent("   ").toJson(listaDto)
            val file = File(AppConflig.outputData + File.separator +"Profesor.json")
            if (!file.exists()) {
                file.createNewFile()
            }
            file.delete()
            file.writeText(json)
            println("JSON guardado en ${file.absolutePath}")
        }
        override fun exportToXml(data: List<Profesor>) {
            logger.debug { "Exportando a XML" }
            val file = File(AppConflig.outputData+ File.separator+"Profesor.xml")
            if (!file.exists()) {
                file.createNewFile()
            }
            val serializer = Persister()
            var listaDTO:MutableList<ProfesorDTO> = mutableListOf()
            data.forEach { listaDTO+= it.toDTO() }
            val escritura: ProfesoresDTOList = ProfesoresDTOList(listaDTO)
            serializer.write(escritura, file)
            println("XML guardado en ${file.absolutePath}")
        }
        override fun loadData(): List<Profesor> {
            logger.debug { "Creando directorio ${AppConflig.inputData}" }
            // ¿Existe el fichero???
            val file = File(AppConflig.outputData + File.separator + "Coches.csv")
            if (!file.exists()) {
               file.createNewFile()
            }
            return file.readLines().drop(1)
                .map {fila ->
                    fila.split(",")
                }.map { columnas ->
                    Profesor(
                            id =columnas[0].toLong(),
                            nombre = columnas[1],
                            fehcaIncorpracion = LocalDate.parse(columnas[2])
                        )
                }.toMutableList()
        }
        @OptIn(ExperimentalStdlibApi::class)
        override fun saveData(data: List<Profesor>): Boolean {
            logger.debug { "save Data" }
            logger.debug { "Creando directorio ${AppConflig.outputData}" }
            Files.createDirectories(Paths.get(AppConflig.outputData))
            val file = File(AppConflig.outputData + File.separator + "Profesor.json")
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter<List<ProfesorDTO>>()
            file.writeText(
                jsonAdapter.indent("    ").toJson(data.map { it.toDTO() })
            )
            return true
        }
    }
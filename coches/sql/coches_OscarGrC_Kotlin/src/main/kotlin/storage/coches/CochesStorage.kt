package storage.coches

import Dto.CocheDTO
import Dto.CochesDTOList
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import config.AppConflig
import mappers.toDTO
import models.Coche
import models.TipoMotor
import mu.KotlinLogging
import org.simpleframework.xml.core.Persister
import storage.conductores.IConductorStorage
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

object CochesStorage: ICochesStorage {
    private val logger= KotlinLogging.logger {  }

        override fun exportToCsv(data: List<Coche>) {
            logger.debug { "Creando directorio ${AppConflig.outputData}" }
            // Creamos el directorio data si no existe
            Files.createDirectories(Paths.get(AppConflig.outputData))
            val file = File(AppConflig.outputData + File.separator + "Coches.csv")
            if(!file.exists())file.createNewFile()
            logger.debug { "Saving directorio ${file.name}" }
            file.writeText("ID,marca,modelo,precio,motor\n")
            data.forEach {
               file.appendText("${it.id},${it.marca},${it.modelo},${it.precio},${it.motor.name}\n")
            }}
        @OptIn(ExperimentalStdlibApi::class)
        override fun exportToJson(data: List<Coche>) {
            logger.debug { "Exportando a JSON" }
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter<List<CocheDTO>>()
            var listaDto:MutableList<CocheDTO> = mutableListOf()
            data.forEach { listaDto+=it.toDTO() }
            val json = jsonAdapter.indent("   ").toJson(listaDto)
            val file = File(AppConflig.outputData + File.separator +"Coches.json")
            if (!file.exists()) {
                file.createNewFile()
            }
            file.delete()
            file.writeText(json)
            println("JSON guardado en ${file.absolutePath}")
        }
        override fun exportToXml(data: List<Coche>) {
            logger.debug { "Exportando a XML" }
            val file = File(AppConflig.outputData+ File.separator+"Personas.xml")
            if (!file.exists()) {
                file.createNewFile()
            }
            val serializer = Persister()
            var listaDTO:MutableList<CocheDTO> = mutableListOf()
            data.forEach { listaDTO+= it.toDTO() }
            val escritura: CochesDTOList = CochesDTOList(listaDTO)
            serializer.write(escritura, file)
            println("XML guardado en ${file.absolutePath}")
        }
        override fun loadData(): List<Coche> {
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
                    //sexamos Alumnos y Profesores por el primer campo que sera o un int o un nombre de la enum class
                        Coche(
                            id=columnas[0].trim().toLong(),
                            marca = columnas[1],
                            modelo = columnas[2],
                            precio = columnas[3].toDouble(),
                            motor = TipoMotor.valueOf(columnas[4])
                        )
                }.toMutableList()
        }
        @OptIn(ExperimentalStdlibApi::class)
        override fun saveData(data: List<Coche>): Boolean {
            logger.debug { "save Data" }
            logger.debug { "Creando directorio ${AppConflig.outputData}" }
            Files.createDirectories(Paths.get(AppConflig.outputData))
            val file = File(AppConflig.outputData + File.separator + "Coches.json")
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter<List<CocheDTO>>()
            file.writeText(
                jsonAdapter.indent("    ").toJson(data.map { it.toDTO() })
            )
            return true
        }
    }
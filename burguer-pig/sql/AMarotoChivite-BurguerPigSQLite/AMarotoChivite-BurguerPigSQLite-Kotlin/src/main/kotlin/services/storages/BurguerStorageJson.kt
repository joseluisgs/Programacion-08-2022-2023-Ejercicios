package services.storages

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import config.ConfigApp
import models.Burguer
import services.storages.base.IStorageGeneral
import java.io.File

private val logger = mu.KotlinLogging.logger {}

open class BurguerStorageJson : IStorageGeneral<Burguer> {

    override fun writeFile(data: List<Burguer>): Boolean {
        logger.debug { "Storage: Escribiendo en JSON" }

        val localFileToWrite = ConfigApp.getPathDataOutput("json")
        val fileToWrite = File(localFileToWrite)

        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonString = gson.toJson(data)
        fileToWrite.writeText(jsonString)

        return true
    }

    override fun readFile(): List<Burguer> {
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
        val type = object : TypeToken<List<Burguer>>() {}.type
        return gson.fromJson(jsonString, type)
    }

}
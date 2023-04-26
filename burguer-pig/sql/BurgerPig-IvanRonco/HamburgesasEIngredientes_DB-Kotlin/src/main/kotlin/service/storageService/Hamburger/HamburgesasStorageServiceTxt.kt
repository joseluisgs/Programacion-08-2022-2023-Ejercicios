package service.storageService.Hamburger

import config.ConfigApp
import model.Hamburgesa
import model.Ingrediente
import mu.KotlinLogging
import storageService.Hamburger.HamburgesaStorageService
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*

class HamburgesasStorageServiceTxt(
    private val configApp: ConfigApp
): HamburgesaStorageService {

    private val logger = KotlinLogging.logger {  }

    private val file = File(configApp.APP_DATA+ File.separator+"hamburgesas.txt")

    override fun saveAll(entities: List<Hamburgesa>) {
        logger.debug { "Guardo toda la info en el fichero CSV" }

        file.writeText("")
        entities.forEach {
            file.appendText(it.id.toString()+"\n")
            file.appendText(it.nombre+"\n")
            it.ingredientes.forEach {ing ->
                file.appendText("*"+"\n")
                file.appendText(ing.id.toString()+"\n")
                file.appendText(ing.nombre+"\n")
                file.appendText(ing.precio.toString()+"\n")
            }
            file.appendText("/"+"\n")
        }
    }

    override fun loadAll(): List<Hamburgesa> {
        logger.debug { "Consigo toda la info del fichero CSV" }

        if(!file.exists()) return emptyList()
        val hamburgesas = mutableListOf<Hamburgesa>()
        BufferedReader(FileReader(file)).use {
            while(it.ready()){
                val id = UUID.fromString(it.readLine())
                val nombre = it.readLine()
                val ingredientes = mutableListOf<Ingrediente>()
                while(it.readLine() != "/"){
                    ingredientes.add(
                        Ingrediente(
                            id = it.readLine().toLong(),
                            nombre = it.readLine(),
                            precio = it.readLine().toDouble()
                        )
                    )
                }
                hamburgesas.add(
                    Hamburgesa(
                        id = id,
                        nombre = nombre,
                        ingredientes = ingredientes
                    )
                )
            }
        }
        return hamburgesas
    }
}
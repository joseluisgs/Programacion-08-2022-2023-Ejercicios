package config

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

object AppConfig {
    private lateinit var dbPath: String
    private lateinit var dataPath: String
    lateinit var connectionUrl: String
    lateinit var jsonPath: String
    lateinit var csvPath: String

    init {
        loadProperties()
        initStorage()
    }

    private fun loadProperties() {
        val props = Properties()
        props.load(ClassLoader.getSystemResourceAsStream("config.properties"))
        dbPath = props.getProperty("db.path", "database")
        connectionUrl = System.getProperty("db.url",
                "jdbc:sqlite:database${File.separator}Coches.db")
        dataPath = props.getProperty("data.path", "data")
        jsonPath = dataPath + File.separator + props.getProperty("json.path", "json")
        csvPath = ClassLoader.getSystemResource("coches.csv")?.file
            ?: throw Exception("Fichero coches.csv no encontrado")
    }

    private fun initStorage() {
        Files.createDirectories(Paths.get(dbPath))
        Files.createDirectories(Paths.get(jsonPath))
    }
}

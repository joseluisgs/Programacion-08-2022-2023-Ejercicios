package config

import exceptions.CsvNotFoundException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

object AppConfig {
    lateinit var appData: String
    lateinit var csvPath: String
    lateinit var dbUrl: String
    lateinit var dbPath: String

    init {
        loadProperties()
        initStorage()
    }

    private fun loadProperties() {
        val properties = Properties()
        properties.load(ClassLoader.getSystemResourceAsStream("config.properties"))
        appData = properties.getProperty("data.dir", "data")
        csvPath = ClassLoader.getSystemResource("coches.csv")?.file
            ?: throw CsvNotFoundException("No existe el archivo coches.csv")
        dbUrl = properties.getProperty("db.url", "jdbc:sqlite:database/coches.db")
        dbPath = properties.getProperty("db.path", "database")
    }

    private fun initStorage() {
        Files.createDirectories(Paths.get(appData))
        Files.createDirectories(Paths.get(dbPath))
    }
}

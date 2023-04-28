package config

import mu.KotlinLogging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

private val logger = KotlinLogging.logger{}

object AppConfig {
    private var _dataInput: String = "data"
    val dataInput: String get() = _dataInput

    private var _dataOutput: String = "data"
    val dataOutput get() = _dataOutput

    private var _databaseUrl: String = "jdbc:sqlite:database.db"
    val databaseUrl get() = _databaseUrl

    private var _databaseDropTable: Boolean = false
    val databaseDropTable get() = _databaseDropTable

    init {
        loadProperties()
        initStorage()
    }

    private fun initStorage() {
        Files.createDirectories(Paths.get(AppConfig.dataOutput + File.separator + "ingredientes" + File.separator))
        Files.createDirectories(Paths.get(AppConfig.dataOutput + File.separator + "hamburguesa" + File.separator))
    }

    private fun loadProperties() {
        logger.info("Cargando configuración Ingrediente")

        val properties = Properties()

        properties.load(AppConfig::class.java.getResourceAsStream("/application.properties"))

        _dataInput = properties.getProperty("data.input", "data")
        _dataOutput = properties.getProperty("data.output", "data")
        _databaseUrl = properties.getProperty("database.url", "jdbc:sqlite:database.db")
        _databaseDropTable = properties.getProperty("database.droptable", "false").toBoolean()
    }
}
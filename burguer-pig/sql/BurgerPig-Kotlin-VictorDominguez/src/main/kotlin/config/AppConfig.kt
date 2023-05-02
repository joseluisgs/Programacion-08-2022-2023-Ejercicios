package config

import mu.KotlinLogging
import java.util.Properties

private val logger = KotlinLogging.logger{}

object AppConfig {
    lateinit var app_name: String
    lateinit var app_version: String
    lateinit var app_author: String
    lateinit var app_data: String
    lateinit var app_url: String
    lateinit var app_init_url: String

    init {
        loadProperties()
    }

    private fun loadProperties() {
        logger.debug { "AppConfig -> Cargando configuración inicial de la aplicación" }
        val properties = Properties()
        properties.load(AppConfig::class.java.getResourceAsStream("/config.properties"))
        app_name = properties.getProperty("app.name") ?: "BurgerPig"
        app_version = properties.getProperty("app.version") ?: "1.0"
        app_author = properties.getProperty("app.author") ?: "VictorDominguez"
        app_data = properties.getProperty("app.storageDir") ?: "data"
        app_url = properties.getProperty("app.url") ?: "jdbc:sqlite:hamburguesas.db"
        app_init_url = properties.getProperty("app.init.url") ?: "initSqlScript.sql"
    }
}
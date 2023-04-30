package config

import mu.KotlinLogging
import java.util.Properties

private val logger = KotlinLogging.logger {}

object AppConfig {
    lateinit var APP_NAME: String
    lateinit var APP_VERSION: String
    lateinit var APP_AUTHOR: String
    lateinit var APP_DATA: String
    lateinit var APP_URL: String
    lateinit var INIT_URL: String

    init {
        loadProperties()
    }

    private fun loadProperties() {
        logger.debug { "AppConfig -> Cargando configuración" }
        val properties = Properties()
        properties.load(AppConfig::class.java.getResourceAsStream("/config.properties"))
        APP_NAME = properties.getProperty("app.name") ?: "CochesSql"
        APP_VERSION = properties.getProperty("app.version") ?: "1.0"
        APP_AUTHOR = properties.getProperty("app.author") ?: "VictorDominguez"
        APP_DATA = properties.getProperty("app.storageDir") ?: "data"
        APP_URL =  properties.getProperty("app.url") ?: "jdbc:sqlite:coches.db"
        INIT_URL = properties.getProperty("app.init.url") ?: "initSqlScript.sql"
    }


}
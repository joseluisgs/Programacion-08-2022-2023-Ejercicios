package services.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import config.ConfigApp
import database.AppDatabase
import database.ModuloQueries

private val logger = mu.KotlinLogging.logger { }

object ModuloSqlDelightClient {

    lateinit var moduloQueries: ModuloQueries
    private val connectionUrl = ConfigApp.getUrlDbConection()

    init {
        logger.debug { "${this::class.simpleName}: Inicializando el gestor de Bases de Datos" }

        initConfig()

        // Borramos la base de datos si está en la config=true
        if (ConfigApp.getDeleteDb()) {
            removeAllData()
        }
    }

    private fun initConfig() {
        val driver = JdbcSqliteDriver(connectionUrl)
        logger.debug { "${this::class.simpleName}: Creando Base de Datos" }
        AppDatabase.Schema.create(driver)
        val database = AppDatabase(driver)

        moduloQueries = database.moduloQueries
    }

    private fun removeAllData() {
        logger.debug { "${this::class.simpleName}: Eliminando todos los datos" }

        moduloQueries.transaction {
            moduloQueries.removeAll()
        }
    }
}
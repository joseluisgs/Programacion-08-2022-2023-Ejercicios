package service.database.ingredientes

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import appDatabase.IngredientesQueries
import config.ConfigApp
import dev.ivanrc.database.Database
import mu.KotlinLogging
import repositorio.ingredientes.IngredienteRepositorySQLite.deleteAll
import java.io.FileInputStream
import java.sql.DatabaseMetaData
import java.sql.DriverManager
import java.time.temporal.TemporalQueries
import java.util.*

object IngredientesDatabase {

    val config = ConfigApp

    val logger = KotlinLogging.logger {  }

    private val initDatabase = config.APP_INIT_DATABASE

    val connection get() = DriverManager.getConnection(config.APP_URL)

    lateinit var ingredientesQueries: IngredientesQueries

    init {
        initConfig()
    }

    private fun initConfig() {
        ingredientesQueries = JdbcSqliteDriver(config.APP_URL).let { driver ->
            Database.Schema.create(driver)
            Database(driver)
        }.ingredientesQueries

        if (initDatabase) {
            ingredientesQueries.deleteAllIngredientes()
        }
    }
}
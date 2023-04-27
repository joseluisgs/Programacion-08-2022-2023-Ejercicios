package service.database.profesores

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import config.ConfigApp
import database.ProfesorQueries
import dev.ivanrc.database.Database
import mu.KotlinLogging
import java.sql.DriverManager
import java.time.temporal.TemporalQueries

object ProfesoresDatabase {

    val config = ConfigApp

    val logger = KotlinLogging.logger {  }

    val connection get() = DriverManager.getConnection(config.APP_URL)

    private var initDatabase = true

    lateinit var profesoresQueries: ProfesorQueries

    init {
        initConfig()
    }

    private fun initConfig() {
        profesoresQueries = JdbcSqliteDriver(config.APP_URL).let { driver ->
            Database.Schema.create(driver)
            Database(driver)
        }.profesorQueries

        if (initDatabase) {
            profesoresQueries.deleteAll()
        }
    }
}
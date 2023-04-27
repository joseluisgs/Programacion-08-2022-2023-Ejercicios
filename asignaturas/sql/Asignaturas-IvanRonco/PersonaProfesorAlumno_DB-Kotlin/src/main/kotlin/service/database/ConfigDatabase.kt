package service.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import config.ConfigApp
import database.GeneralDatabaseQueries
import dev.ivanrc.database.AppDatabase
import mu.KotlinLogging
import java.sql.DriverManager

object ConfigDatabase {

    private val logger = KotlinLogging.logger {  }

    private val config = ConfigApp

    val connection get() = DriverManager.getConnection(config.APP_URL)

    lateinit var queries : GeneralDatabaseQueries

    private val initDatabase = config.APP_INIT_DATA

    init{
        initDatabase()
    }

    fun initDatabase(){
        logger.debug { "Se inicia la DB" }

        queries = JdbcSqliteDriver(config.APP_URL).let { driver ->
            AppDatabase.Schema.create(driver)
            AppDatabase(driver)
        }.generalDatabaseQueries

        if(initDatabase){
            queries.transaction {
                queries.deleteAllProfesores()
                queries.deleteAllAlumnos()
            }
        }
    }

}
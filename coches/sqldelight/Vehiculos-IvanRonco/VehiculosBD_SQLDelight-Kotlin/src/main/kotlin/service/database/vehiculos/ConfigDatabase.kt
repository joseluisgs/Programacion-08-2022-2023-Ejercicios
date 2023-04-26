package service.database.vehiculos

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import config.ConfigApp
import database.VehiculoQueries
import dev.ivanrc.database.Database
import models.vehiculo.Vehiculo
import mu.KotlinLogging
import java.io.BufferedReader
import java.io.FileReader
import java.io.Reader
import java.sql.DriverManager
import java.util.Properties

object ConfigDatabase {

    val config = ConfigApp

    val logger = KotlinLogging.logger {  }

    val connection get() = DriverManager.getConnection(config.APP_URL)

    private val initDatabase = config.APP_INIT_DATABASE

    lateinit var vehiculoQuery: VehiculoQueries

    init{
        initDatabase()
    }

    fun initDatabase(){
        logger.debug { "Se inicia la DB." }

        vehiculoQuery = JdbcSqliteDriver(config.APP_URL).let { driver ->
            Database.Schema.create(driver)
            Database(driver)
        }.vehiculoQueries

        if(initDatabase){
            vehiculoQuery.deleteAllVehiculos()
            vehiculoQuery.deleteAllMotores()
        }
    }
}
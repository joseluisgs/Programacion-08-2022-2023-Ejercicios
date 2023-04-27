package service.database.vehiculos

import config.ConfigApp
import mu.KotlinLogging
import org.apache.ibatis.jdbc.ScriptRunner
import java.io.BufferedReader
import java.io.FileReader
import java.io.Reader
import java.sql.DriverManager
import java.util.Properties

object ConfigDatabase {

    val config = ConfigApp

    val logger = KotlinLogging.logger {  }

    val connection get() = DriverManager.getConnection(config.APP_URL)

    fun initDatabase(sqlScript: String = config.APP_INIT_SCRIPT){
        logger.debug { "Se inicia la DB, según el script del fichero: $sqlScript" }
        val sr = ScriptRunner(connection)
        //Preguntar porque necesito el "database.initScript" en el Properties().getProperty()
        val initScript = Properties().getProperty("database.initScript", ClassLoader.getSystemResource(sqlScript).file)
        val reader: Reader = BufferedReader(FileReader(initScript))
        sr.runScript(reader)
    }
}
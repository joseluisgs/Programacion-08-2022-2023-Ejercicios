package services.storage.database

import config.AppConfig
import mu.KotlinLogging
import org.apache.ibatis.jdbc.ScriptRunner
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.sql.DriverManager

private val logger = KotlinLogging.logger{}

object DataBaseManager {
    val connection get() = DriverManager.getConnection(AppConfig.APP_URL)
    val initSqlScript: String = "${System.getProperty("user.dir")}${File.separator}src${File.separator}main${File.separator}resources${File.separator}initSqlScript.sql"

    init {
        logger.debug { "DataBaseManager -> Ejecutando Script SQL inicial" }
        ejecutarSql(initSqlScript)
    }

    private fun ejecutarSql(sql: String) {
        val sr = ScriptRunner(connection)
        val script = BufferedReader(FileReader(sql))
        sr.runScript(script)
    }
}
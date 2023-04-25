package repositories.profesor

import config.AppConfig
import factories.ProfesorFactory
import factories.ProfesorFactory.getProfesoresDefault
import org.apache.ibatis.jdbc.ScriptRunner
import org.junit.jupiter.api.BeforeEach
import services.database.DataBaseManager
import java.io.BufferedReader
import java.io.FileReader
import java.io.Reader

class ProfesorRepositoryDataBaseTest: ProfesorRepositoryGenericTest() {
    override fun getRepository() = ProfesorRepositoryDataBase

    @BeforeEach
    fun tearDown(){
        // Eliminar tablas
        executeSQLFile(AppConfig.APP_DB_RESET_PATH)
        // Crear tablas
        executeSQLFile(AppConfig.APP_DB_INIT_PATH)
        // Limpiar tablas (no es necesario)
        ProfesorRepositoryDataBase.deleteAll()
        // Insertar datos
        ProfesorRepositoryDataBase.saveAll(getProfesoresDefault())
    }

    private fun executeSQLFile(sqlFile: String ){
        val sr = ScriptRunner(DataBaseManager.dataBase)
        val reader: Reader = BufferedReader(FileReader(sqlFile))
        sr.runScript(reader)
    }
}
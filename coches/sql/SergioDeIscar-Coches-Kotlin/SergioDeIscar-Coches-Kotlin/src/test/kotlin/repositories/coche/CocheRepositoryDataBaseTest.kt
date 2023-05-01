package repositories.coche

import config.AppConfig
import factories.CocheFactory.getCochesDefault
import factories.ConductorFactory.getConductorDefault
import models.Coche
import org.apache.ibatis.jdbc.ScriptRunner
import org.junit.jupiter.api.BeforeEach
import repositories.CrudRepository
import repositories.RepositoryGenericTest
import repositories.conductor.ConductorRepositoryDataBase
import services.database.DataBaseManager
import java.io.BufferedReader
import java.io.FileReader
import java.io.Reader

class CocheRepositoryDataBaseTest: RepositoryGenericTest<Coche, Long>() {
    @BeforeEach
    override fun setUp() {
        // Eliminar tablas
        executeSQLFile(AppConfig.APP_DB_RESET_PATH)
        // Crear tablas
        executeSQLFile(AppConfig.APP_DB_INIT_PATH)
        // Guardar en base de datos los conductores por defecto
        ConductorRepositoryDataBase.saveAll(getConductorDefault())
        val paco = ConductorRepositoryDataBase.findAll()
        val repaco = CocheRepositoryDataBase.findAll()
        super.repository = CocheRepositoryDataBase
        //super.setUp()
    }

    private fun executeSQLFile(sqlFile: String ){
        val sr = ScriptRunner(DataBaseManager.dataBase)
        val reader: Reader = BufferedReader(FileReader(sqlFile))
        sr.runScript(reader)
    }

    override fun getRepository() = CocheRepositoryDataBase

    override fun getDefault() = getCochesDefault()

    override fun getUpdated() = getCochesDefault()[0].copy(marca = "Ferrari")

    override fun getNew() = getCochesDefault()[0].copy(id = 5L, marca = "Ferrari")

    override fun getOkId() = 1L

    override fun getFailId() = 100L
}
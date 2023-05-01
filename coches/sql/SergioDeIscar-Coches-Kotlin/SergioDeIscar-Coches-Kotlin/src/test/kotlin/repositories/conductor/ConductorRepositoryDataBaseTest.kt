package repositories.conductor

import config.AppConfig
import factories.CocheFactory.getCochesDefault
import factories.ConductorFactory.getConductorDefault
import models.Coche
import models.Conductor
import org.apache.ibatis.jdbc.ScriptRunner
import org.junit.jupiter.api.BeforeEach
import repositories.CrudRepository
import repositories.RepositoryGenericTest
import repositories.coche.CocheRepositoryDataBase
import services.database.DataBaseManager
import java.io.BufferedReader
import java.io.FileReader
import java.io.Reader
import java.util.UUID

class ConductorRepositoryDataBaseTest: RepositoryGenericTest<Conductor, UUID>() {
    @BeforeEach
    fun tearDown(){
        // Eliminar tablas
        executeSQLFile(AppConfig.APP_DB_RESET_PATH)
        // Crear tablas
        executeSQLFile(AppConfig.APP_DB_INIT_PATH)
        // Limpiar tablas (no es necesario)
        ConductorRepositoryDataBase.deleteAll()
        // Insertar datos
        ConductorRepositoryDataBase.saveAll(getConductorDefault())
    }

    private fun executeSQLFile(sqlFile: String ){
        val sr = ScriptRunner(DataBaseManager.dataBase)
        val reader: Reader = BufferedReader(FileReader(sqlFile))
        sr.runScript(reader)
    }

    override fun getRepository() = ConductorRepositoryDataBase

    override fun getDefault() = getConductorDefault()
    override fun getUpdated() = getConductorDefault()[0].copy(nombre = "Pedro")

    override fun getNew() = getConductorDefault()[0].copy(uuid = UUID.fromString("7feb0567-4fac-4f68-87bf-12094ecde25d"), nombre = "Pedro")

    override fun getOkId() = UUID.fromString("2ccc8134-e398-434c-b853-a0732f32ca50")

    override fun getFailId() = UUID.fromString("00000000-0000-0000-0000-000000000100")
}
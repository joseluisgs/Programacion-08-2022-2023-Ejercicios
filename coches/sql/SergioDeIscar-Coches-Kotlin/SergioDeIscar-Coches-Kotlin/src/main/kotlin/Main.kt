import controllers.coche.CocheController
import controllers.conductor.ConductorController
import repositories.coche.CocheRepositoryDataBase
import repositories.conductor.ConductorRepositoryDataBase
import services.storage.coche.CocheFileCsv
import services.storage.conductor.ConductorFileCsv
import services.storage.conductor.ConductorFileJson
import services.storage.conductor.ConductorFileXml
import java.util.*

fun main(){
    val conductorControllerCsv = ConductorController(
        ConductorRepositoryDataBase,
        ConductorFileCsv
    )
    val conductorControllerJson = ConductorController(
        ConductorRepositoryDataBase,
        ConductorFileJson
    )
    val conductorControllerXML = ConductorController(
        ConductorRepositoryDataBase,
        ConductorFileXml
    )
    conductorControllerCsv.importData()

    conductorControllerCsv.exportData()
    conductorControllerJson.exportData()
    conductorControllerXML.exportData()
}
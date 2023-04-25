import com.github.michaelbull.result.get
import com.github.michaelbull.result.onSuccess
import controllers.CrudController
import controllers.modulo.ModuloController
import controllers.profesor.ProfesorController
import models.Grado
import repositories.modulo.ModuloRepositoryDataBase
import repositories.modulo.ModuloRepositoryMap
import repositories.profesor.ProfesorRepositoryDataBase
import repositories.profesor.ProfesorRepositoryMap
import services.storage.modulo.ModuloFileCsv
import services.storage.modulo.ModuloFileJson
import services.storage.modulo.ModuloFileXml
import services.storage.modulo.ModuloStorageService
import services.storage.profesor.ProfesorFileCsv
import services.storage.profesor.ProfesorFileJson
import services.storage.profesor.ProfesorFileXML
import services.storage.profesor.ProfesorStorageService
import java.util.*

fun main() {
    val profesorControllerCsv = ProfesorController(
        ProfesorRepositoryDataBase,
        ProfesorFileCsv
    )
    val profesorControllerXml = ProfesorController(
        ProfesorRepositoryDataBase,
        ProfesorFileXML
    )
    val profesorControllerJson = ProfesorController(
        ProfesorRepositoryDataBase,
        ProfesorFileJson
    )
    // Importa los datos del archivo csv y los guarda en la base de datos
    profesorControllerCsv.importData()

    // Exporta los datos de la base de datos al archivo csv, xml y json
    profesorControllerCsv.exportData()
    profesorControllerXml.exportData()
    profesorControllerJson.exportData()

}


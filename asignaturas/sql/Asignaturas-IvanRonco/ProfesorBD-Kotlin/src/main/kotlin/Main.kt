import controller.ProfesoresController
import factory.ProfesoresFactory
import repositorio.profesores.ProfesoresRepositorySQLite
import service.storage.profesores.ProfesorStorageServiceCsv
import service.storage.profesores.ProfesoresStorageServiceJson
import service.storage.profesores.ProfesoresStorageServiceXml

@OptIn(ExperimentalStdlibApi::class)
fun main(args: Array<String>) {

    val profesores = ProfesoresFactory.createSome()

    val controller = ProfesoresController(
        ProfesoresRepositorySQLite,
        ProfesoresStorageServiceJson()
    )

    profesores.forEach {
        controller.save(it)
    }

    controller.findAll().forEach { println(it) }

    controller.exportData()

    controller.findAll().forEach { println(it) }

    controller.findByExperiencia(12).forEach { println(it) }

    try{
        println(controller.findById(12))
    }catch (e: Exception){
        println(e.message)
    }

    controller.importData(true)

    controller.findAll().forEach { println(it) }
}
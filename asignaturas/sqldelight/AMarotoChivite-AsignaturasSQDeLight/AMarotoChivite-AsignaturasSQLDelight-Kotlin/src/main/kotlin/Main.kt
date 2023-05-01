import config.ConfigApp
import controllers.DocenciaController
import controllers.ModuloController
import controllers.ProfesorController
import models.Docencia
import models.Modulo
import models.Profesor
import repositories.DocenciaRepository
import repositories.ModuloRepository
import repositories.ProfesorRepository
import services.storages.DocenciaStorageJson
import services.storages.ModuloStorageCsv
import services.storages.ProfesorStorageCsv
import java.time.LocalDate
import java.util.*

fun main() {
    // Cargamos config
    ConfigApp

    // Inicializo los controladores
    val controllerProfesor = ProfesorController(ProfesorRepository(), ProfesorStorageCsv())
    val controllerModulo = ModuloController(ModuloRepository(), ModuloStorageCsv())

    // Guardamos items en CSV, haré la prueba con un profesor uqe imparta en dos módulos
    controllerProfesor.saveItem(Profesor(1, "profesor_1", LocalDate.now()))
    controllerModulo.saveItem(Modulo(UUID.randomUUID(), "modulo_1", 1, Modulo.TypeGrado.DAM))
    controllerModulo.saveItem(Modulo(UUID.randomUUID(), "modulo_2", 2, Modulo.TypeGrado.DAM))

    // Escribimos en CSV para iniciar el enunciado!
    controllerProfesor.exportFile()
    controllerModulo.exportFile()

    // Aquí inicia el enunciado, leemos del CSV y exportamos JSON los datos leídos
    val listProfesores = controllerProfesor.importFile()
    val listModulos = controllerModulo.importFile()

    // Asignamos la docencia a nuestro profesor y módulo (lo introduzco en nuestra base de datos)
    val controllerDocencia = DocenciaController(DocenciaRepository(), DocenciaStorageJson())

    // El primer profesor impartirá en todos los módulos
    val listDocencias = mutableListOf<Docencia>()
    listModulos.forEach {
        listDocencias.add(Docencia(listProfesores[0].id, it.uuid, it.curso, it.grado))
    }

    // Guardamos las docencias en nuestra base de datos
    listDocencias.forEach {
        controllerDocencia.saveItem(it)
    }

    // Exportamos a Json los datos
    controllerDocencia.exportFile()
}
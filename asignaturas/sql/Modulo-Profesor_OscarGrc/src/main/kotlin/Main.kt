import controllers.ProfesorController
import controllers.ModuloController
import models.Grado
import models.Modulo
import models.Profesor
import repositories.ProfesorRepository
import repositories.ModuloRepository
import storage.profesor.ProfesorStorage
import storage.modulo.ModuloStorage
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/***
Resuelve usando SQL nada más.

Profesor (id autonumérico, nombre, fehcaIncorpracion)
Módulo (uuid, nombre, curso (1,2), grado (DAM, DAW, ASIR, SMR))
Debes tener en cuenta que un profesor puede dar varios módulos y un módulo puede ser impartido por varios profesores (si hay varios grupos) Te recomiendo que rompas la relación muchos a muchos con una tabla docencia, donde se tenga en cuenta profesor, módulo y grupo

Lee los datos del profesor los leerás del CSV Los datos del módulo los leeras de un CSV

Debes sacar la docencia completa en JSON con los datos embebidos

NOTA: Si te cuesta esto imagina que un módulo solo puede estar impartido por un profesor
 */
fun main(args: Array<String>) {

    val controller = ProfesorController(ProfesorRepository, ProfesorStorage)
    println(controller.findAll())
    println(controller.findById(1) ?: "No existe el usuario")
    println(controller.save(Profesor(id = 0, nombre = "Javier",
        fehcaIncorpracion =  LocalDate.parse("2000/06/14", DateTimeFormatter.ofPattern("yyyy/MM/dd")))))
    println(controller.save(Profesor(id = 0, nombre = "Carmen",
        fehcaIncorpracion =  LocalDate.parse("2010/05/12", DateTimeFormatter.ofPattern("yyyy/MM/dd")))))
    println(controller.save(Profesor(id = 0, nombre = "JoseL",
        fehcaIncorpracion =  LocalDate.parse("2015/09/01", DateTimeFormatter.ofPattern("yyyy/MM/dd")))))
    println(controller.findAll())
    println(controller.findById(1) ?: "No existe el usuario")
    println(controller.update(Profesor(id = 1, nombre = "Borja",
        fehcaIncorpracion =  LocalDate.parse("2022/09/01", DateTimeFormatter.ofPattern("yyyy/MM/dd")))))
    println(controller.findAll())
    println(controller.update(Profesor(id = 0, nombre = "Borja",
        fehcaIncorpracion =  LocalDate.parse("2022/09/01", DateTimeFormatter.ofPattern("yyyy/MM/dd")))))
    println(controller.deleteById(1))
    println(controller.findAll())
    println(controller.deleteById(-1))
    // Escribir en csv los datos
    controller.exportToCsv()
    //en json
    controller.exportToJson()
    // en xml
    controller.exportToXml()
    // cargamos de csv
    var lista2 = controller.loadData()
    println(lista2.toString())

// ahora el controlador Conductores

    val controller2 = ModuloController(ModuloRepository, ModuloStorage)
    println(controller2.findAll())
    println(controller2.findByUUID(UUID.randomUUID().toString()) ?: "No existe el usuario")
    println(controller2.save(
        Modulo(uuid = UUID.randomUUID(), nombre = "Pericles", curso = 1.toString(), grado = Grado.DAM)))
    println(controller2.save(Modulo(uuid =UUID.randomUUID(),nombre = "JoseL", curso = 1.toString(),grado = Grado.ASIR)))
    println(controller2.save(Modulo(uuid = UUID.randomUUID(), nombre = "Carmen", curso = 2.toString(),grado=Grado.DAW)))
    var listamodulos = controller2.findAll()
    println(controller2.findAll())
    println(controller2.findByUUID(listamodulos[2].uuid.toString()) ?: "No existe el usuario")
    println("")
    println("UPDATE")
    println(controller2.findAll())

    println(controller2.update(Modulo(uuid=listamodulos[2].uuid,nombre="Carmen2", curso=1.toString(),grado=Grado.DAM)))
    println(controller2.findAll())
    println(controller2.update(Modulo(uuid=UUID.randomUUID(),nombre="Carmen2", curso=1.toString(),grado=Grado.DAM)))
    println(controller2.deleteByUUID(listamodulos[0].uuid.toString()))
    println(controller2.findAll())
    println(controller2.deleteByUUID(UUID.randomUUID().toString()))
    // Escribir en csv los datos
    controller2.exportToCsv()
    //en json
    controller2.exportToJson()
    // en xml
    controller2.exportToXml()
    // cargamos de csv
    var lista3 = controller2.loadData()
    println(lista3.toString())



}
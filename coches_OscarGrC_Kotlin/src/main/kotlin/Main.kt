import controllers.CocheController
import controllers.ConductorController
import models.Coche
import models.Conductor
import models.TipoMotor
import repositories.CocheRepository
import repositories.ConductorRepository
import storage.coches.CochesStorage
import storage.conductores.ConductorStorage
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/***
 * Resuelve usando SQL nada más.

Coche (id autonumérico, marca, modelo, precio, TipoMotor (gasolina, diése, híbrido, eléctrico))
Cinductor(uuid, nombre, fechaCarnet)
Un coche solo puede estar conducido por un conductor y un conductor puede conducir varios coches.

Lee los datos de conductor de un CSV Saca los datos de coche con los datos del conductor embebidos en JSON
 */
fun main(args: Array<String>) {
    val controller = CocheController(CocheRepository, CochesStorage)
    println(controller.findAll())
    println(controller.findById(1) ?: "No existe el usuario")
    println(controller.save(Coche(marca = "Seat", modelo = "Leon", precio = 23000.34, motor = TipoMotor.GASOLINA)))
    println(controller.save(Coche(marca = "Peugeot", modelo = "205", precio = 2000.0, motor = TipoMotor.DIESEL)))
    println(controller.save(Coche(marca = "Seat", modelo = "Ibiza", precio = 12000.0, motor = TipoMotor.HIBRIDO)))
    println(controller.findAll())
    println(controller.findById(1) ?: "No existe el usuario")
    println(controller.update(Coche(id = 1,marca = "Seat", modelo = "Leon",
                                        precio = 20000.0, motor = TipoMotor.GASOLINA)))
    println(controller.findAll())
    println(controller.update(Coche(id = -1,marca = "Seat", modelo = "Leon",
                                        precio = 20000.0, motor = TipoMotor.GASOLINA)))
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

    val controller2 = ConductorController(ConductorRepository, ConductorStorage)
    println(controller2.findAll())
    println(controller2.findByUUID(UUID.randomUUID().toString()) ?: "No existe el usuario")
    println(controller2.save(Conductor(uuid = UUID.randomUUID(), nombre = "Pericles",
        LocalDate.parse("2000/06/14", DateTimeFormatter.ofPattern("yyyy/MM/dd")))))
    println(controller2.save(Conductor(uuid = UUID.randomUUID(), nombre = "Loli",
        LocalDate.parse("2020/01/21", DateTimeFormatter.ofPattern("yyyy/MM/dd")))))
    println(controller2.save(Conductor(uuid = UUID.randomUUID(), nombre = "Nacho",
        LocalDate.parse("1990/10/24", DateTimeFormatter.ofPattern("yyyy/MM/dd")))))
    var listaconductores = controller2.findAll()
    println(controller2.findAll())
    println(controller2.findByUUID(listaconductores[2].uuid.toString()) ?: "No existe el usuario")
    println("")
    println("UPDATE")
    println(controller2.findAll())

    println(controller2.update(Conductor(uuid = listaconductores[2].uuid, nombre = "Nacho2",
        LocalDate.parse("2990-10-24", DateTimeFormatter.ofPattern("yyyy-MM-dd")))))
    println(controller2.findAll())
    println(controller2.update(Conductor(uuid = UUID.randomUUID(), nombre = "Nacho2",
        LocalDate.parse("2990-10-24", DateTimeFormatter.ofPattern("yyyy-MM-dd")))))
    println(controller2.deleteByUUID(listaconductores[0].uuid.toString()))
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
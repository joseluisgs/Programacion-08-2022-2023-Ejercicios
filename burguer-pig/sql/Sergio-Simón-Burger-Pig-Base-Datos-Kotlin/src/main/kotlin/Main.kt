import controllers.HamburguesaController
import controllers.IngredienteController
import factories.createIngredienteRandom
import models.Hamburguesa
import models.Ingrediente
import mu.KotlinLogging
import repositories.hamburguesa.HamburguesasRepositoryImplement
import repositories.ingredientes.IngredienteRepositoryImplement
import service.storage.hamburgueas.HamburguesasJson
import service.storage.hamburgueas.HamburguesasXml
import service.storage.ingredientes.IngredientesJson
import service.storage.ingredientes.IngredientesStorageImplement
import service.storage.ingredientes.IngredientesXml

private val logger = KotlinLogging.logger{}

@ExperimentalStdlibApi
fun main() {
    createCsv()
    export()
    ingredientesConsultas()
}

@ExperimentalStdlibApi
fun export() {
//    exportIngredienteJson()
//    exportIngredienteXml()
    exportHamburguesaJson()
//    exportHamburguesaXml()
}

fun exportIngredienteXml() {
    val ingredienteController = IngredienteController(
        IngredienteRepositoryImplement(),
        IngredientesXml()
    )

    val ingredienteRandom: MutableList<Ingrediente> = mutableListOf()
    repeat(2) {
        ingredienteRandom.add(createIngredienteRandom())
    }
    ingredienteController.exportData(ingredienteRandom)

    ingredienteController.importData()
    val ingredienteImport = ingredienteController.getAll()
    ingredienteController.exportData(ingredienteImport)

    ingredienteController.getAll().forEach{ println(it) }
}

fun exportIngredienteJson() {
    val ingredienteController = IngredienteController(
        IngredienteRepositoryImplement(),
        IngredientesJson()
    )

    val ingredienteRandom: MutableList<Ingrediente> = mutableListOf()
    repeat(2) {
        ingredienteRandom.add(createIngredienteRandom())
    }
    ingredienteController.exportData(ingredienteRandom)

    ingredienteController.importData()
    val ingredienteImport = ingredienteController.getAll()
    ingredienteController.exportData(ingredienteImport)

    ingredienteController.getAll().forEach{ println(it) }
}

@ExperimentalStdlibApi
private fun exportHamburguesaJson() {
    val ingredienteController = IngredienteController(
        ingredienteRepository = IngredienteRepositoryImplement(),
        ingredienteStorage = IngredientesStorageImplement()
    )

    val hamburguesaController = HamburguesaController(
        IngredienteRepositoryImplement(),
        HamburguesasRepositoryImplement(),
        HamburguesasJson()
    )

    ingredienteController.importData()

    val receta = ingredienteController.getAll()

    val hamburguesas: MutableList<Hamburguesa> = mutableListOf()

    val hamburguesa = hamburguesaController.save(receta)
    hamburguesas.add(hamburguesa)
    hamburguesaController.exportData(hamburguesas)

    println(hamburguesaController.loadData())
}

fun exportHamburguesaXml() {
    val ingredienteController = IngredienteController(
        ingredienteRepository = IngredienteRepositoryImplement(),
        ingredienteStorage = IngredientesStorageImplement()
    )

    val hamburguesaController = HamburguesaController(
        IngredienteRepositoryImplement(),
        HamburguesasRepositoryImplement(),
        HamburguesasXml()
    )

    ingredienteController.importData()

    val receta = ingredienteController.getAll()

    val hamburguesas: MutableList<Hamburguesa> = mutableListOf()

    val hamburguesa = hamburguesaController.save(receta)
    hamburguesas.add(hamburguesa)
    hamburguesaController.exportData(hamburguesas)

    println(hamburguesaController.loadData())
}

private fun createCsv(){
    val ingredienteController = IngredienteController(
        IngredienteRepositoryImplement(),
        IngredientesStorageImplement()
    )


    val ingredienteRandom: MutableList<Ingrediente> = mutableListOf()
    repeat(2) {
        ingredienteRandom.add(createIngredienteRandom())
    }
    ingredienteController.exportData(ingredienteRandom)
}

private fun ingredientesConsultas() {
    val ingredienteController = IngredienteController(
        IngredienteRepositoryImplement(),
        IngredientesStorageImplement()
    )

    ingredienteController.importData()

    val exportData = ingredienteController.getAll()

    exportData.forEach {
        println(it)
    }

    println("Numero de ingredientes: " + ingredienteController.contar())
    println("¿Existe el ingrediente con id: 1? " + ingredienteController.existsById(1))
    println("Guardando un nuevo ingrediente: " + ingredienteController.save(createIngredienteRandom()))
    println("Obteniendo el ingrediente con id: 3" + ingredienteController.findById(3))
    println(
        "Borrando el ingrediente con id: 3 " + ingredienteController.deleteById(3) + " ¿Existe el ingrediente con id: 3? " + ingredienteController.existsById(
            3
        )
    )

    val ingredienteList: MutableList<Ingrediente> = mutableListOf()
    repeat(2) {
        ingredienteList.add(createIngredienteRandom())
    }
    ingredienteController.saveAll(ingredienteList)

    var borrar: Ingrediente = createIngredienteRandom()

    val exportData2 = ingredienteController.getAll()

    exportData2.forEach {
        println(it)
        if (it.id == 4L) {
            borrar = it
        }
    }

    logger.warn { "Vamos a borrar $borrar" }
    ingredienteController.delete(borrar)

    val exportData3 = ingredienteController.getAll()

    exportData3.forEach {
        println(it)
        if (it.id == 4L) {
            borrar = it
        }
    }

    logger.warn { "Vamos a borrar todo" }
    ingredienteController.deleteAll(exportData3)

    val exportData4 = ingredienteController.getAll()

    exportData4.forEach {
        println(it)
    }
}
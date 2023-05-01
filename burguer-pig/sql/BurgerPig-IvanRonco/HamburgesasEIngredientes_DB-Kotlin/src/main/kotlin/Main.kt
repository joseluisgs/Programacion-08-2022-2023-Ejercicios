import config.ConfigApp
import controller.HamburgesaController
import factory.HamburgesaFactory
import factory.IngredienteFactory
import modules.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    startKoin {
        printLogger()
        modules(myModule)
    }

    App().runApp()
}

class App: KoinComponent {

    val configApp: ConfigApp = get()

    val controller: HamburgesaController by inject()

    fun runApp() {
        val ingredientes = IngredienteFactory.createSomeRandom()

        val hamburgesa1 = HamburgesaFactory.createRandom(ingredientes)
        val hamburgesa2 = HamburgesaFactory.createRandom(ingredientes)
        val hamburgesa3 = HamburgesaFactory.createRandom(ingredientes)
        val hamburgesa4 = HamburgesaFactory.createRandom(ingredientes)
        val hamburgesa5 = HamburgesaFactory.createRandom(ingredientes)

        val hamburgesas = listOf(hamburgesa1, hamburgesa2, hamburgesa3, hamburgesa4, hamburgesa5)

        hamburgesas.forEach { println(it) }

        println()
        println()

        hamburgesas.forEach {
            controller.save(it)
        }

        controller.export()

        controller.getAll().forEach { println(it) }

        println()

        controller.save(hamburgesa3.copy(id = hamburgesa1.id))

        println()

        controller.getAll().forEach { println(it) }

        println()

        controller.deleteAll()

        controller.getAll().forEach { println(it) }
    }
}
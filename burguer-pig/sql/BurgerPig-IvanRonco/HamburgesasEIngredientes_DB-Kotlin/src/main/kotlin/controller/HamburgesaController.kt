package controller

import exception.hamburgesaException.HamburgesaNotFoundException
import exception.ingredienteException.IngredienteNotFoundException
import model.Hamburgesa
import model.Ingrediente
import mu.KotlinLogging
import service.database.ConfigDatabase
import service.repository.Hamburger.HamburgesaRepository
import service.repository.Ingrediente.IngredienteRepository
import storageService.Hamburger.HamburgesaStorageService
import validator.validate
import java.util.*

class HamburgesaController(
    private val repositoryHamburgesa: HamburgesaRepository,
    private val hamburgesaStorage: HamburgesaStorageService,
    private val repositoryIngrediente: IngredienteRepository
) {

    private val logger = KotlinLogging.logger {  }

    fun findByName(name: String): List<Hamburgesa> {
        logger.debug { "Controller: Se buscan ingredientes según su nombre." }

        return repositoryHamburgesa.findByName(name).toHamburgesasConLosIngredientes()
    }

    fun findById(id: UUID): Hamburgesa {
        logger.debug { "Controller: Se busca una hamburgesa según su id." }

        return repositoryHamburgesa.findById(id)?.let{
            it.toHamburgesaConLosIngredientes()
        } ?: run{
            throw HamburgesaNotFoundException(id.toString())
        }
    }

    fun getAll(): List<Hamburgesa> {
        logger.debug { "Controller: Se consiguen hamburgesas." }

        return repositoryHamburgesa.getAll().toHamburgesasConLosIngredientes()
    }

    private fun getAllIngredientesByHamburgesaId(id: UUID): List<Long> {
        logger.debug { "Controller: Consigo todos los ids de los ingredientes de la hamburgesa con id: $id" }

        return repositoryHamburgesa.getAllIdIngredientesByHamburgesaId(id)
    }

    fun save(entity: Hamburgesa): Hamburgesa{
        logger.debug { "Controller: Se añade/actualiza una hamburgesa." }

        entity.validate()
        val ingredientes = mutableListOf<Ingrediente>()
        entity.ingredientes.forEach {
            if(!ingredientes.map { it.nombre.lowercase() }.contains(it.nombre.lowercase())) {
                val ingredientesCompletos = repositoryIngrediente.getAll()
                if(!ingredientesCompletos.map { it.nombre.lowercase() }.contains(it.nombre.lowercase())){
                    ingredientes.add(repositoryIngrediente.save(it))
                }else{
                    ingredientes.add(ingredientesCompletos.first{ing -> ing.nombre.lowercase() == it.nombre.lowercase()})
                }
            }
        }
        val hamburgesa = repositoryHamburgesa.save(entity.copy(ingredientes = ingredientes))

        return hamburgesa
    }

    fun delete(id: UUID): Boolean {
        logger.debug { "Controller: Se elimina una hamburgesa." }

        return if(repositoryHamburgesa.delete(id)) true else throw HamburgesaNotFoundException(id.toString())
    }

    fun deleteAll() {
        logger.debug { "Controller: Se eliminan todas las hamburgesas." }

        return repositoryHamburgesa.deleteAll()
    }

    fun import(){
        logger.debug { "Almacenamos el contenido de los ficheros en la DB" }

        val hamburgesas = hamburgesaStorage.loadAll()

        hamburgesas.forEach {
            save(it)
        }
    }

    fun export(){
        logger.debug { "Almacenamos el contenido de la DB en los ficheros" }

        val hamburgesas = getAll()

        hamburgesaStorage.saveAll(hamburgesas)
    }

    private fun List<Hamburgesa>.toHamburgesasConLosIngredientes(): List<Hamburgesa> {
        logger.debug { "Una vez hemos conseguido todas las hamburgesas, sin sus ingredientes, la añadimos sus ingredientes correspondientes" }

        return this.map {
            it.toHamburgesaConLosIngredientes()
        }
    }

    private fun Hamburgesa.toHamburgesaConLosIngredientes(): Hamburgesa {
        logger.debug { "Le añadimos sus ingredientes correspondientes a la hamburgesa de id: ${this.id}" }

        val ingredientes = mutableListOf<Ingrediente>()
        getAllIngredientesByHamburgesaId(this.id).forEach {id ->
            ingredientes.add(repositoryIngrediente.findById(id) ?: throw IngredienteNotFoundException(id.toString()))
        }
        return this.copy(ingredientes = ingredientes)
    }
}

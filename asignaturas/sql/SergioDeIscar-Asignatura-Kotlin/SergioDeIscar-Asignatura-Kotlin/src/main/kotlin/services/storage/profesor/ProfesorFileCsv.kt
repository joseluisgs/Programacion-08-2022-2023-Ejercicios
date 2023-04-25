package services.storage.profesor

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import config.AppConfig
import controllers.modulo.ModuloController
import dto.ModulosDto
import dto.ProfesorDto
import dto.ProfesoresDto
import mappers.toClass
import mappers.toDto
import models.Modulo
import models.Profesor
import mu.KotlinLogging
import repositories.modulo.ModuloRepository
import repositories.modulo.ModuloRepositoryMap
import services.storage.modulo.ModuloFileCsv
import java.io.File
import java.io.IOException
import java.util.*

private val LOCAL_PATH = "${System.getProperty("user.dir")}${File.separator}"
private val LOCAL_PATH_RESOURCES = "${LOCAL_PATH}src${File.separator}main${File.separator}resources${File.separator}"

private val logger = KotlinLogging.logger {  }

object ProfesorFileCsv: ProfesorStorageService{
    private val localPath = "${AppConfig.APP_DATA}${File.separator}profesor.csv"
    private val resourcesPath = "${LOCAL_PATH_RESOURCES}profesor.csv"

    override fun saveAll(elements: List<Profesor>): List<Profesor> {
        logger.debug { "ProfesorFileCsv ->\tsaveAll" }
        val file = File(localPath)
        if (file.exists() && !file.canWrite()) throw IOException("ERROR: ProfesorFileCsv ->\tNo se puede escribir en el fichero CSV")
        file.writeText("nombre,incorporación,modulo\n")
        elements.map { it.toDto() }.forEach {
            file.appendText(
                "${it.nombre},${it.fechaIncorporacion},${it.modulos.modulos.joinToString(separator = "|") { it.uuid }}\n"
            )
        }
        return elements
    }

    override fun loadAll(): List<Profesor> {
        logger.debug { "ProfesorFileCsv ->\tloadAll" }
        val file = File(resourcesPath)
        if (!file.exists() || !file.canRead()) throw IOException("ERROR: ProfesorFileCsv ->\tNo se puede leer en el fichero CSV")
        return file.readLines()
            .drop(1)
            .map { it.split(",") }
            .map { ProfesorDto(
                nombre = it[0],
                fechaIncorporacion = it[1],
                modulos =  getModulos(it[2])
            ) }.map { it.toClass() }
    }

    private fun getModulos(csv: String): ModulosDto {
        // No se si esto es correcto...
        val modulos = mutableListOf<Modulo>()
        val moduloController = ModuloController(
            ModuloRepositoryMap(),
            ModuloFileCsv
        )
        moduloController.importData()
        csv.split("|").forEach { uuid ->
            moduloController.findById(UUID.fromString(uuid))
                .onSuccess {
                    modulos.add(it)
                }
                .onFailure {
                    logger.error { "ERROR: ${it.message}" }
                }
        }

        return ModulosDto(modulos.map { it.toDto() })
    }
}
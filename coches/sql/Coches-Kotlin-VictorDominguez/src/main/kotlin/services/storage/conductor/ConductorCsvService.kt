package services.storage.conductor

import enums.TipoMotor
import models.Coche
import models.Conductor
import mu.KotlinLogging
import services.storage.StorageService
import java.io.File
import java.time.LocalDate
import java.util.*

private val logger = KotlinLogging.logger{}

object ConductorCsvService: ConductoresService {

    private val path = "${System.getProperty("user.dir")}${File.separator}src${File.separator}main${File.separator}resources${File.separator}conductores.csv"
    private val fichero = File(path)

    override fun exportar(items: List<Conductor>) {
        logger.debug { "ConductorCsvService -> Exportando datos a CSV" }
    }

    override fun importar(): List<Conductor> {
        logger.debug { "ConductorCsvService -> Importando datos de CSV" }
        return fichero.readLines()
            .drop(1)
            .map { linea -> linea.split(";") }
            .map { columnas ->
                Conductor(
                    UUID.fromString(columnas[0]),
                    columnas[1],
                    LocalDate.parse(columnas[2]),
                    mutableListOf()
                )
            }
    }

}
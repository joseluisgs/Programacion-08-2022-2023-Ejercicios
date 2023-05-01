package services.storage

import config.AppConfig
import models.Coche
import models.Conductor
import java.io.File
import java.time.LocalDate
import java.util.*

object CocheCsv : CocheStorage {
    private val path = AppConfig.csvPath
    override fun save(items: List<Coche>) {
        TODO("not needed")
    }

    override fun load(): List<Coche> {
        return File(path).useLines {
            it.drop(1).map { line ->
                val (marca, modelo, precio, tipoMotor) = line.split(",")
                Coche(
                    marca = marca,
                    modelo = modelo,
                    precio = precio.toDouble(),
                    tipoMotor = Coche.TipoMotor.valueOf(tipoMotor),
                    conductor = Conductor(
                        UUID.fromString("00000000-0000-0000-0000-000000000000"),
                        "default",
                        LocalDate.parse("2023-01-01")
                    )
                )
            }.toList()
        }
    }
}

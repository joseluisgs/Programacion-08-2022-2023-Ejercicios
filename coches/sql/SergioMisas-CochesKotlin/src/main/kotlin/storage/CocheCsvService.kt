package storage

import config.AppConfig
import models.Coche
import models.Conductor
import java.io.File
import java.time.LocalDate
import java.util.*

object CocheCsvService : CocheStorageService {
    private val path = AppConfig.csvPath + File.separator + "coches.csv"

    override fun guardar(items: List<Coche>) {
        TODO("No es necesario")
    }

    override fun cargar(): List<Coche> {
        return File(path).useLines {
            it.drop(1).map { coche ->
                val (marca, modelo, precio, motor) = coche.split(",")
                Coche(
                    marca = marca,
                    modelo = modelo,
                    precio = precio.toDouble(),
                    motor = Coche.TipoMotor.valueOf(motor),
                    conductor = Conductor(
                        UUID.fromString("00000000-0000-0000-0000-000000000000"),
                        "Default",
                        LocalDate.parse("2000-01-01")
                    )
                )
            }
        }.toList()
    }
}

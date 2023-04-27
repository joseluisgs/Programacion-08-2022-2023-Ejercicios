package factories

import factories.CocheFactory.getCochesDefault
import models.Conductor
import java.time.LocalDate
import java.util.UUID

object ConductorFactory {
    fun getConductorDefault() = listOf(
        Conductor(
            UUID.fromString("2ccc8134-e398-434c-b853-a0732f32ca50"),
            "Pepe",
            LocalDate.parse("2020-01-01"),
            getCochesDefault().filter { it.idConductor == UUID.fromString("2ccc8134-e398-434c-b853-a0732f32ca50") }
        ),
        Conductor(
            UUID.fromString("5c9de3ca-60ee-43be-813d-32d1e092efcd"),
            "Juan",
            LocalDate.parse("2020-01-01"),
            getCochesDefault().filter { it.idConductor == UUID.fromString("5c9de3ca-60ee-43be-813d-32d1e092efcd") }
        ),
        Conductor(
            UUID.fromString("411a1904-42fb-4714-8648-145528653da2"),
            "Maria",
            LocalDate.parse("2020-01-01"),
            getCochesDefault().filter { it.idConductor == UUID.fromString("411a1904-42fb-4714-8648-145528653da2") }
        ),
        Conductor(
            UUID.fromString("e0820eaa-7f17-48a3-a512-63f8ffe28ac9"),
            "Luis",
            LocalDate.parse("2020-01-01"),
            emptyList()
        )
    )
}
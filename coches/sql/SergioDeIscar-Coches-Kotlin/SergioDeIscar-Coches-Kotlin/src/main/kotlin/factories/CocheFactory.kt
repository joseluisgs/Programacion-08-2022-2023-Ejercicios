package factories

import models.Coche
import models.TypeMotor
import java.util.*

object CocheFactory {
    fun getCochesDefault() = listOf(
        Coche(
            1,
            "Audi",
            "A4",
            20000f,
            TypeMotor.DIESEL,
            UUID.fromString("2ccc8134-e398-434c-b853-a0732f32ca50")
        ),
        Coche(
            2,
            "Seat",
            "Ibiza",
            12000f,
            TypeMotor.DIESEL,
            UUID.fromString("2ccc8134-e398-434c-b853-a0732f32ca50")
        ),
        Coche(
            3,
            "Renault",
            "Megane",
            15000f,
            TypeMotor.GASOLINA,
            UUID.fromString("5c9de3ca-60ee-43be-813d-32d1e092efcd")
        ),
        Coche(
            4,
            "Ford",
            "Focus",
            14000f,
            TypeMotor.GASOLINA,
            UUID.fromString("411a1904-42fb-4714-8648-145528653da2")
        )
    )
}
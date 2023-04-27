package models

import java.time.LocalDate
import java.util.*

data class Conductor(
    val uuid: UUID,
    val nombre: String = "",
    val fechaCarnet: LocalDate = LocalDate.parse("2023-01-01")
)

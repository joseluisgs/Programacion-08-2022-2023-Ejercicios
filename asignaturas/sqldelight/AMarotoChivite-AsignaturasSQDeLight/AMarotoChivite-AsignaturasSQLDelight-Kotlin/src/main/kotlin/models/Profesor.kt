package models

import java.time.LocalDate

data class Profesor(
    val id: Long = 0L,
    val name: String,
    val dateInit: LocalDate = LocalDate.now()
)
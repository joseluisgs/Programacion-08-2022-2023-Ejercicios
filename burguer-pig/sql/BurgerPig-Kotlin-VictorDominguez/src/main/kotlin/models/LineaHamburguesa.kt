package models

import java.util.UUID

data class LineaHamburguesa(
    val id: Long,
    val id_ham: UUID,
    val id_ing: Long,
    val cantidad: Int
)
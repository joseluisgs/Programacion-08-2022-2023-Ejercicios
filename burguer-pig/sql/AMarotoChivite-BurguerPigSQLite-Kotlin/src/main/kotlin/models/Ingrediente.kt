package models

import java.time.LocalDateTime

// Se guarda en otr base de datos independiente a burguer
data class Ingrediente (
    // El id es auto-numerico en la base de datos, es indiferente el valor que introduzcamos al crear el objeto
    val id: Long = 0,
    val name: String,
    val price: Double,
    val stock: Int = 0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val avaliable: Boolean = true
)
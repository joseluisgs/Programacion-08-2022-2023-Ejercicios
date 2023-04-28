package models

import java.time.LocalDate
import java.util.*

data class Conductor(
    val uuid: UUID,
    val nombre: String,
    val fechaCarnet:LocalDate
){
    override fun toString(): String {
        return "Soy $nombre con uuid" +
                " ${uuid.toString().substring(startIndex = 0, endIndex = 9)}con carnet desde $fechaCarnet"
    }
}
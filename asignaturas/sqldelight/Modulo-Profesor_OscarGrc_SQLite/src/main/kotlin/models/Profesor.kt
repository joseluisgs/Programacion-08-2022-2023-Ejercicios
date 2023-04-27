package models

import java.time.LocalDate

data class Profesor(val id: Long = 0, val nombre:String, val fehcaIncorpracion:LocalDate){
    override fun toString(): String {
        return "Soy $nombre, con id $id me incorpore $fehcaIncorpracion \n"
    }
}
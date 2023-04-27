package models

import java.time.LocalDate
import java.util.UUID

class Conductor(val uuid: UUID, val nombre: String, val fechaCarnet: LocalDate) {
    override fun toString(): String {
        return "Conductor(uuid=$uuid, fechaCarnet=$fechaCarnet)"
    }
}
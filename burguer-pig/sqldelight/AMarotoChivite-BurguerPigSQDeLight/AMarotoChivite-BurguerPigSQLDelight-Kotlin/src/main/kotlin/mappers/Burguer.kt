package mappers

import database.BurguerTable
import models.Burguer
import java.util.*

fun toBurguer(burguer: BurguerTable): Burguer {
    return Burguer(
       UUID.fromString(burguer.uuid),
        burguer.name,
        burguer.stock.toInt()
    )
}
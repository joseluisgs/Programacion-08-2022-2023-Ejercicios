package mappers

import database.IngredienteTable
import database.LineaBurguerTable
import models.Ingrediente
import models.LineaBurguer
import java.time.LocalDateTime
import java.util.*

fun toLineaBurguer(lineaBurguer: LineaBurguerTable): LineaBurguer {

    return LineaBurguer(
        lineaBurguer.linea_id,
        UUID.fromString(lineaBurguer.burguer_uuid),
        lineaBurguer.ingrediente_id.toLong(),
        lineaBurguer.ingrediente_quantity.toInt(),
        lineaBurguer.ingrediente_price.toDouble()
        )
}
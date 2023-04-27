package models

import java.util.*

// En la base de datos, guardamos en una tabla la BURGUER y en otra tabla LINEABURGUER
data class Burguer(
    val uuid: UUID,
    val name: String,
    val stock: Int,
    val lineaBurguer: MutableList<LineaBurguer> = mutableListOf()
) {

    private var priceCalculate: Double = lineaBurguer.sumOf { it.ingredientePrice } * 1.5

    fun getPrice(): Double {
        return priceCalculate
    }

    fun addLinea(linea: LineaBurguer) {
        lineaBurguer.add(linea)
    }

    fun removeLinea(idLineaToDelete: Long) {
        lineaBurguer.removeIf { it.lineaId == idLineaToDelete }
    }

    override fun toString(): String {
        return "Burguer(uuid=$uuid, name='$name', stock=$stock, lineaBurguer=$lineaBurguer, priceCalculate=$priceCalculate)"
    }

}
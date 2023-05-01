package dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "LineaHamburguesa")
data class LineaHamburguesaDto(
    @field:Attribute(name = "lineaId")
    @param:Attribute(name = "lineaId")
    val lineaId: String,

    @field:Attribute(name = "idIngrediente")
    @param:Attribute(name = "idIngrediente")
    val idIngrediente: String,

    @field:Attribute(name = "idHamburguesas")
    @param:Attribute(name = "idHamburguesas")
    val idHamburguesa: String,

    @field:Attribute(name = "precioIngrediente")
    @param:Attribute(name = "precioIngrediente")
    val precioIngrediente: String,

    @field:Attribute(name = "cantidadIngrediente")
    @param:Attribute(name = "cantidadIngrediente")
    val cantidadIngrediente: String,

    @field:Attribute(name = "precioTotal")
    @param:Attribute(name = "precioTotal")
    val precioTotal: String
)

@Root(name = "List_LineaHamburguesaDto")
data class LineaHamburguesaListDto(
    @field:ElementList(name = "Linea_Hamburguesa", inline = true)
    @param:ElementList(name = "Linea_Hamburguesa", inline = true)
    val lineaHamburguesa: List<LineaHamburguesaDto>
)
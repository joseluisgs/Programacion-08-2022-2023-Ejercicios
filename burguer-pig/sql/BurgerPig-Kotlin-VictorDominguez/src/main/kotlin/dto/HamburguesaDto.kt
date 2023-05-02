package dto

import models.Ingrediente
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "hamburguesa")
data class HamburguesaDto(

    @field:Attribute(name = "id")
    @param:Attribute(name = "id")
    val id: String,

    @field:Element(name = "nombre")
    @param:Element(name = "nombre")
    val nombre: String,

    @field:ElementList(name = "ingredientes")
    @param:ElementList(name = "ingredientes")
    val ingredientes: IngredienteListDto,

    @field:Element(name = "precio")
    @param:Element(name = "precio")
    val precio: String
)

@Root(name = "hamburguesas")
data class HamburguesaListDto(
    @field:ElementList(name = "hamburguesas", inline = true)
    @param:ElementList(name = "hamburguesas", inline = true)
    val hamburguesas: List<HamburguesaDto>
)
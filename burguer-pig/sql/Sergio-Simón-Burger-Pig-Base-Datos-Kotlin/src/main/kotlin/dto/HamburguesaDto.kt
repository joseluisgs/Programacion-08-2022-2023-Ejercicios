package dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "Hamburguesa")
data class HamburguesaDto(
    @field:Attribute(name = "id")
    @param:Attribute(name = "id")
    val id: String,

    @field:Attribute(name = "uuid")
    @param:Attribute(name = "uuid")
    val uuid: String,

    @field:Attribute(name = "name")
    @param:Attribute(name = "name")
    val name: String,

    @field:Attribute(name = "cantidad")
    @param:Attribute(name = "cantidad")
    val cantidad: String,

    @field:ElementList(name = "lineaIngrediente", inline = true)
    @param:ElementList(name = "lineaIngrediente", inline = true)
    val lineaHamburguesa: List<LineaHamburguesaDto>,

    @field:Attribute(name = "createdAt")
    @param:Attribute(name = "createdAt")
    val createdAt: String,

    @field:Attribute(name = "updatedAt")
    @param:Attribute(name = "updatedAt")
    val updatedAt: String,

    @field:Attribute(name = "precio")
    @param:Attribute(name = "precio")
    val precio: String

)

@Root(name = "List_Hamburguesas")
data class HamburguesasListDto(
    @field:ElementList(name = "List_Hamburguesas", inline = true)
    @param:ElementList(name = "List_Hamburguesas", inline = true)
    val listHamburguesas: List<HamburguesaDto>
)
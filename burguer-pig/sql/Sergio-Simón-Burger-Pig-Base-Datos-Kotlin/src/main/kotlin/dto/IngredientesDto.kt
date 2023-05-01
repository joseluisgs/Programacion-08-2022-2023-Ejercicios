package dto

import locale.toLocalMoney
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.util.*

@Root(name = "Ingredientes")
data class IngredientesDto(
    @field:Attribute(name = "id")
    @param:Attribute(name = "id")
    val id: Long,

    @field:Attribute(name = "uuid")
    @param:Attribute(name = "uuid")
    val uuid: String,

    @field:Attribute(name = "name")
    @param:Attribute(name = "name")
    val name: String,

    @field:Attribute(name = "price")
    @param:Attribute(name = "price")
    val price: String,

    @field:Attribute(name = "disponible")
    @param:Attribute(name = "disponible")
    val disponible: String,

    @field:Attribute(name = "cantidad")
    @param:Attribute(name = "cantidad")
    val cantidad: String,

    @field:Attribute(name = "createdAt")
    @param:Attribute(name = "createdAt")
    val createdAt: String,

    @field:Attribute(name = "updatedAt")
    @param:Attribute(name = "updatedAt")
    val updatedAt: String
)

@Root(name = "List_Ingredientes")
data class ListIngredientesDto(
    @field:ElementList(name = "List_Ingredientes", inline = true)
    @param:ElementList(name = "List_Ingredientes", inline = true)
    val listIngredientesDto: List<IngredientesDto>
)
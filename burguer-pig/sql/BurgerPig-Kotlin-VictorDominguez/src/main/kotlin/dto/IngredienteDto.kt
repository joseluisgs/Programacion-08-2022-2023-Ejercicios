package dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ingrediente")
data class IngredienteDto(

    @field:Attribute(name = "id")
    @param:Attribute(name = "id")
    val id: String,

    @field:Element(name = "nombre")
    @param:Element(name = "nombre")
    val nombre: String,

    @field:Element(name = "precio")
    @param:Element(name = "precio")
    val precio: String
)

@Root(name = "ingredientes")
data class IngredienteListDto(
    @field:ElementList(name = "ingredientes", inline = true)
    @param:ElementList(name = "ingredientes", inline = true)
    val ingredientes: List<IngredienteDto>
)
package dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "profesor")
data class ProfesorDto(
    @field:Attribute(name = "id")
    @param:Attribute(name = "id")
    val id: String = "0",

    @field:Element(name = "nombre")
    @param:Element(name = "nombre")
    val nombre: String,

    @field:Element(name = "incorporacion")
    @param:Element(name = "incorporacion")
    val fechaIncorporacion: String,

    @field:Element(name = "modulo")
    @param:Element(name = "modulo")
    val modulos: ModulosDto
)
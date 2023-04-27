package dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "modulo")
data class ModuloDto(
    @field:Attribute(name = "uuid")
    @param:Attribute(name = "uuid")
    val uuid: String,

    @field:Element(name = "nombre")
    @param:Element(name = "nombre")
    val nombre: String,

    @field:Element(name = "curso")
    @param:Element(name = "curso")
    val curso: String,

    @field:Element(name = "grado")
    @param:Element(name = "grado")
    val grado: String
)
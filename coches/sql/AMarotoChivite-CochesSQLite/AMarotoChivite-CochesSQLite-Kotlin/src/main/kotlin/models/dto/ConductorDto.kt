package models.dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element

data class ConductorDto(
    @field:Attribute(name = "uuid")
    @param:Attribute(name = "uuid")
    val uuid: String,
    @field:Element(name = "name")
    @param:Element(name = "name")
    val name: String
)
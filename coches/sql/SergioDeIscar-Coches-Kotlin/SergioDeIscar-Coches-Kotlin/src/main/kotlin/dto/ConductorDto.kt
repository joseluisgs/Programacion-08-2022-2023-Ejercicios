package dto

import models.Coche
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.time.LocalDate
import java.util.*

@Root(name = "ConductorDto")
data class ConductorDto(
    @field:Attribute(name = "UUID")
    @param:Attribute(name = "UUID")
    val uuid: String,

    @field:Element(name = "nombre")
    @param:Element(name = "nombre")
    val nombre: String,

    @field:Element(name = "fechaCarnet")
    @param:Element(name = "fechaCarnet")
    val fechaCarnet: String,

    @field:Element(name = "coches", required = false)
    @param:Element(name = "coches", required = false)
    val coches: CochesDto?
)
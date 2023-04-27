package dto

import models.TypeMotor
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "coche")
data class CocheDto(
    @field:Attribute(name = "id")
    @param:Attribute(name = "id")
    val id: String = "-1",

    @field:Element(name = "marca")
    @param:Element(name = "marca")
    val marca: String,

    @field:Element(name = "modelo")
    @param:Element(name = "modelo")
    val modelo: String,

    @field:Element(name = "precio")
    @param:Element(name = "precio")
    val precio: String,

    @field:Element(name = "motor")
    @param:Element(name = "motor")
    val motor: String,

    @field:Attribute(name = "idConductor")
    @param:Attribute(name = "idConductor")
    val idConductor: String,
)
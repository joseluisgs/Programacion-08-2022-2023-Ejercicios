package models.dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element

data class VehicleDto(
    @field:Attribute(name = "uuid")
    @param:Attribute(name = "uuid")
    val uuid: String,
    @field:Element(name = "model")
    @param:Element(name = "model")
    val model: String,
    @field:Element(name = "motor")
    @param:Element(name = "motor")
    val motor: String,
    @field:Attribute(name = "fk_uuid_conductor")
    @param:Attribute(name = "fk_uuid_conductor")
    val foreignUuidConductor: String
)
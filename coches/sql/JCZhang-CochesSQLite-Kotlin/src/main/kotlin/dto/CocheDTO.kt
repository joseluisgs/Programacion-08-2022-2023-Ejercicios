package dto

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name = "coche")
data class CocheDTO(

    @field: Element(name = "id")
    @param: Element(name = "id")
    val id: Long,

    @field: Element(name = "marca")
    @param: Element(name = "marca")
    val marca: String,

    @field: Element(name = "modelo")
    @param: Element(name = "modelo")
    val modelo: String,

    @field: Element(name = "precio")
    @param: Element(name = "precio")
    val precio: Double,

    @field: Element(name = "tipoMotor")
    @param: Element(name = "tipoMotor")
    val tipoMotor: String,

)

@Root(name= "coches")
data class  CocheListDto(
    @field: ElementList(name = "coches",  inline= true)
    @param: ElementList(name = "coches", inline= true)
    val coches: List<CocheDTO>
    )